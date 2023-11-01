package services;

import dtos.CustomerDTO;
import dtos.PaymentDTO;
import dtos.PaymentDTOGET;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Customer;
import model.Payment;
import repository.PaymentRepository;
import util.DTOEntityUtil;
import validators.PaymentValidator;

import java.util.Optional;

@Path("payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PaymentService {

    @Inject
    private PaymentRepository paymentRepository;
    @Inject
    private PaymentValidator paymentValidator;

    @POST
    public Response createPayment(@Valid PaymentDTO paymentDTO) {
        try {
            if (paymentValidator.isValidPayment(paymentDTO)) {
              Payment payment=  paymentRepository.createPayment(paymentDTO);
                return Response.status(Response.Status.CREATED)
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

        paymentRepository.deletePayment(id);
        return Response.status(Response.Status.NO_CONTENT)
                .entity("Payment was deleted for accounting.")
                .build();
    }

}

