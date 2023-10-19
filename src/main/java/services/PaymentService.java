package services;


import clienTargetRepository.StoreServiceClientProvider;
import dtos.PaymentDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Customer;
import model.Payment;
import repository.CustomerRepository;
import repository.PaymentRepository;
import validators.PaymentValidator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

@Path("/payments")
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
                paymentRepository.createPayment(paymentDTO);
                return Response.status(Response.Status.CREATED)
                        .entity("Payment created")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid payment data. Only allowed fields: amount (decimal), customer (int), rental (int), staff (int), date (yyyy-MM-dd HH:mm)")
                        .build();
            }
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid payment data. " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Payment getPaymentById(@PathParam("id") int id) {
        return paymentRepository.getPaymentById(id);
    }

    @DELETE
    @Path("/{id}")
    public Response deletePayment(@PathParam("id") int id) {
        if (paymentRepository.getPaymentById(id)!=null) {
            paymentRepository.deletePayment(id);
            return Response.status(Response.Status.NO_CONTENT).entity("Payment was deleted for accounting.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Payment not found.").build();
        }
    }

}

