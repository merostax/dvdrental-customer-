package services;

import clienTargetRepository.StoreServiceClientProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Address;
import model.Customer;
import model.Payment;
import repository.AddressRepository;
import repository.CustomerRepository;

import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CustomerService {
    @Inject
    StoreServiceClientProvider storeServiceClientProvider;


    @Inject
    private CustomerRepository customerRepository;

    @GET
    public List<Customer> listCustomers(@QueryParam("page") @DefaultValue("1") int page) {
        return customerRepository.listCustomers(page);
    }

    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") int id) {
        Customer customer = customerRepository.getCustomerById(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found.")
                    .build();
        }
        return Response.ok(customer).build();
    }


    @POST
    public Response createCustomer(Customer customer, @QueryParam("address") int addressId, @QueryParam("store") int storeId) {
        Response storeResponse = this.storeServiceClientProvider.getStoreServiceTarget()
                .path("stores")
                .path(String.valueOf(storeId))
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (storeResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Bad customer data.").build();
        }
        customerRepository.createCustomer(customer, addressId, storeId);
        return Response.status(Response.Status.CREATED)
                .entity("customer created.")
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Customer customer = customerRepository.getCustomerById(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found.")
                    .build();
        }
        customerRepository.deleteCustomer(id);
        return Response.status(Response.Status.NO_CONTENT)
                .entity("Customer deleted.")
                .build();
    }

    @GET
    @Path("/count")
    public Response getCustomerCount() {
        long count = customerRepository.getCustomerCount();
        return Response.ok(count).build();
    }

    @GET
    @Path("/{id}/payments")
    public List<Payment> getPaymentsForCustomer(@PathParam("id") int id) {
        Customer customer = customerRepository.getCustomerById(id);
        if (customer == null) {
            throw new NotFoundException("Customer not found");
        }
        return customerRepository.getPaymentsByCustomerId(id);
    }


}
