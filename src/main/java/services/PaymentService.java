package services;

import dtos.PaymentDTO;
import dtos.PaymentDTOGET;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Payment;
import repository.PaymentRepository;
import util.DTOEntityUtil;
import util.Hrefs;
import validators.PaymentValidator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

@Path("payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PaymentService {
    @Inject
    Hrefs hrefs;
    @Inject
    DTOEntityUtil DTOEntityUtil;
    @Inject
    private PaymentRepository paymentRepository;
    @Inject
    private PaymentValidator paymentValidator;

    @POST
    public Response createPayment(@Valid PaymentDTO paymentDTO) {
        try {
            if (paymentValidator.isValidPayment(paymentDTO)) {
                Payment payment = paymentRepository.createPayment(paymentDTO);
                return Response.status(Response.Status.CREATED)
                        .header("Location", hrefs.getCustomerHref() != null ? hrefs.getCustomerHref() + "payments/" + payment.getPaymentId() : "")
                        .entity(payment)
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid payment data. Only allowed fields: amount (decimal), customer (int), rental (int), staff (int), date (yyyy-MM-dd HH:mm)")
                        .build();
            }
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Some involved entity does not exist. See message body." + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getPaymentById(@PathParam("id") int id) {
        Optional<Payment> paymentOptional = Optional.ofNullable(paymentRepository.getPaymentById(id));

        if (paymentOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Payment not found.")
                    .build();
        }
        PaymentDTOGET paymentDTOGET = this.createPaymentDTO(paymentOptional.get());
        return Response.ok(paymentDTOGET).build();
    }

    private PaymentDTOGET createPaymentDTO(Payment payment) {
        return DTOEntityUtil.createPaymentDTO(payment);
    }

    @DELETE
    @Path("/{id}")
    public Response deletePayment(@PathParam("id") int id) {
        Optional<Payment> paymentOptional = Optional.ofNullable(paymentRepository.getPaymentById(id));

        if (paymentOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Payment not found.")
                    .build();
        }
        Payment deletedPayment = paymentRepository.getPaymentById(id);
        BigDecimal amountToGiveToCustomer = deletedPayment.getAmount();
        PaymentDTO reversePaymentDTO = new PaymentDTO();
        reversePaymentDTO.setAmount(amountToGiveToCustomer.negate());
        reversePaymentDTO.setCustomer(deletedPayment.getCustomerByCustomerId().getCustomerId());
        reversePaymentDTO.setStaff(deletedPayment.getStaffId());
        reversePaymentDTO.setRental(deletedPayment.getRentalId());
        reversePaymentDTO.setDate(new Timestamp(System.currentTimeMillis()).toString());
        Response createPaymentResponse = createPayment(reversePaymentDTO);
        if (createPaymentResponse.getStatus()==201) {
            String createPaymentLocation = createPaymentResponse.getHeaderString("Location");
            paymentRepository.deletePayment(id);

            return Response.status(Response.Status.NO_CONTENT)
                    .header("Link", "<" + createPaymentLocation + ">")
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create reverse payment.")
                    .build();
        }
    }
}


