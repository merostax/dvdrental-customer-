package services;

import dtos.AddressDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import model.Address;
import model.City;
import model.Country;
import repository.AddressRepository;
import repository.CityRepository;
import repository.CountryRepository;
import util.DTOEntityUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("addresses")
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
    @Context
    UriInfo uriInfo ;
    @GET
    public Response listAddresses(@QueryParam("page") @DefaultValue("1") int page) {
        List<Address> addresses = addressRepository.listAddresses(page);
        List<AddressDTO> addressDTOS= addresses.stream()
                .map(this::createAddressDTO)
                .collect(Collectors.toList());
        return Response.ok(addressDTOS).build();
    }
    private AddressDTO createAddressDTO(Address address) {
        return DTOEntityUtil.createAddressDTO(address);
    }
    @GET
    @Path("/{id}")
    public Response getAddressById(@PathParam("id") int id) {
        Optional<Address> addressOptional = Optional.ofNullable(addressRepository.getAddressById(id));

        if (addressOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Address not found.")
                    .build();
        }
        return Response.ok(this.createAddressDTO(addressOptional.get())).build();
    }

    @POST
    public Response createAddress(AddressDTO addressDTO) {
        Country country = getCountryFromDTO(addressDTO);
        if (country == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Country does not exist.")
                    .build();
        }
        City city = getCityFromDTOAndCountry(addressDTO, country);
        if (city == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("City does not exist or does not belong to the specified country.")
                    .build();
        }
        Address address = DTOEntityUtil.createAddressFROMDTO(addressDTO);
        address.setCity(city);
        Address createdAddress = addressRepository.createAddress(address);
        if (createdAddress != null) {
            return Response.status(Response.Status.CREATED)
                    .entity("Address created.")
                    .header("Location", "/addresses/" + createdAddress.getAddressId())
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create the address.")
                    .build();
        }
    }
    private Country getCountryFromDTO(AddressDTO addressDTO) {
        String countryName = addressDTO.getCountry();
        return countryRepository.findCountryByName(countryName);
    }

    private City getCityFromDTOAndCountry(AddressDTO addressDTO, Country country) {
        String cityName = addressDTO.getCity();
        City city = cityRepository.findCityByName(cityName);
        if (city != null && city.getCountry().equals(country)) {
            return city;
        }
        return null;
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
        Optional<Address> addressOptional = Optional.ofNullable(addressRepository.getAddressById(id));

        if (addressOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Address not found.")
                    .build();
        }

        Address address = addressOptional.get();

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
