package services;

import dtos.AddressDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Address;
import model.City;
import model.Country;
import repository.AddressRepository;
import repository.CityRepository;
import repository.CountryRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AddressService{

    @Inject
    private AddressRepository addressRepository;
    @Inject
    private CountryRepository countryRepository;
    @Inject
    private CityRepository cityRepository;

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
    public Response createAddress(AddressDTO addressDTO) {
        City city = cityRepository.findCityByName(addressDTO.getCity());
        if (city == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Die Stadt existiert nicht")
                    .build();
        }
        Address address = new Address();
        address.setAddress(addressDTO.getAddress());
        address.setAddress2(addressDTO.getAddress2());
        address.setDistrict(addressDTO.getDistrict());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setPhone(addressDTO.getPhone());
        address.setCity(city);

        Country country = countryRepository.findCountryByName(addressDTO.getCountry());
        if (country == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Das Land existiert nicht")
                    .build();
        }
        city.setCountry(country);
        Address createdAddress = addressRepository.createAddress(address);
        return Response.status(Response.Status.CREATED)
                .entity(createdAddress)
                .build();
    }
    @GET
    @Path("/count")
    public Response getAddressCount() {
        long count = addressRepository.getAddressCount();
        return Response.ok(count).build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteAddress(@PathParam("id") int id) {
        Address address = addressRepository.getAddressById(id);

        if (address == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Address not found.")
                    .build();
        }
        if (!addressRepository.isAddressDeletable(address)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Address can't be deleted")
                    .build();
        }

        addressRepository.deleteAddress(id);
        return Response.status(Response.Status.NO_CONTENT)
                .entity("Address deleted.")
                .build();
    }

}
