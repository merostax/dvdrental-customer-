package services;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Payment;
import repository.PaymentRepository;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PaymentService {

    @Inject
    private PaymentRepository paymentRepository;

    @POST
    public Response createPayment(Payment payment) {
        Payment createdPayment = paymentRepository.createPayment(payment);
        return Response.status(Response.Status.CREATED)
                .entity(createdPayment)
                .build();
    }

    @GET
    @Path("/{id}")
    public Payment getPaymentById(@PathParam("id") int id) {
        return paymentRepository.getPaymentById(id);
    }

    @DELETE
    @Path("/{id}")
    public Response deletePayment(@PathParam("id") int id) {
        paymentRepository.deletePayment(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

