package services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Address;
import repository.AddressRepository;

import java.util.List;

@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AddressService {

    @Inject
    private AddressRepository addressRepository;

    @GET
    public List<Address> listAddresses(@QueryParam("page") @DefaultValue("1") int page) {
        return addressRepository.listAddresses(page);
    }

    @GET
    @Path("/{id}")
    public Address getAddressById(@PathParam("id") int id) {
        return addressRepository.getAddressById(id);
    }

    @POST
    public Response createAddress(Address address) {
        Address createdAddress = addressRepository.createAddress(address);
        return Response.status(Response.Status.CREATED)
                .entity(createdAddress)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAddress(@PathParam("id") int id) {
        addressRepository.deleteAddress(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
