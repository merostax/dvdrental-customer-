package services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
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
    private Client client;
    private WebTarget storeServiceTarget;
    public CustomerService(){
        client = ClientBuilder.newClient();
        this.storeServiceTarget = client.target("http://localhost:8082/store/api");
    }

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
        Response storeResponse = this.storeServiceTarget
                .path("stores")
                .path(String.valueOf(storeId))
                .request(MediaType.APPLICATION_JSON)
                .get();
        System.out.println(storeResponse);
        System.out.println(storeResponse.getEntity());
        if (storeResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            return Response.status(storeResponse.getStatus()).entity(storeResponse.getEntity()).build();
        }
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
