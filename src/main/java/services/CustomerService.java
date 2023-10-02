package services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Customer;
import repository.CustomerRepository;

import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CustomerService {

    @Inject
    private CustomerRepository customerRepository;

    @GET
    public List<Customer> listCustomers(@QueryParam("page") @DefaultValue("1") int page) {
        return customerRepository.listCustomers(page);
    }

    @GET
    @Path("/{id}")
    public Customer getCustomerById(@PathParam("id") int id) {
        return customerRepository.getCustomerById(id);
    }

    @POST
    public Response createCustomer(Customer customer, @QueryParam("address") int addressId, @QueryParam("store") int storeId) {
        Customer createdCustomer = customerRepository.createCustomer(customer, addressId, storeId);
        return Response.status(Response.Status.CREATED)
                .entity(createdCustomer)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        customerRepository.deleteCustomer(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
