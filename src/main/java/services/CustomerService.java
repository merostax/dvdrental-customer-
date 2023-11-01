package services;

import clienTargetRepository.StoreServiceClientProvider;
import dtos.CustomerDTO;
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
import util.DTOEntityUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CustomerService {
    @Inject
    StoreServiceClientProvider storeServiceClientProvider;


    @Inject
    private CustomerRepository customerRepository;

    @GET
    public Response listCustomers(@QueryParam("page") @DefaultValue("1") int page) {
        List<Customer> customers = customerRepository.listCustomers(page);
        List<CustomerDTO> customersDTOS = customers.stream().map(this::createCustomerDTO).collect(Collectors.toList());
        return Response.ok(customersDTOS).build();
    }

    private CustomerDTO createCustomerDTO(Customer customer) {
        return DTOEntityUtil.createCustomerDTO(customer);
    }

    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") int id) {
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.getCustomerById(id));

        if (customerOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found.")
                    .build();
        }
        Customer customer = customerOptional.get();
        CustomerDTO customerDTO = this.createCustomerDTO(customer);
        return Response.ok(customerDTO).build();
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
    @GET
    @Path("/count")
    public Response getCustomerCount() {
        long count = customerRepository.getCustomerCount();
        return Response.ok(count).build();
    }

    @GET
    @Path("/{id}/payments")
    public List<Payment> getPaymentsForCustomer(@PathParam("id") int id) {
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.getCustomerById(id));

        if (customerOptional.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }

        return customerRepository.getPaymentsByCustomerId(id);
    }
}
