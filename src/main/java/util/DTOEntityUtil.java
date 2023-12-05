package util;

import dtos.AddressDTO;
import dtos.CustomerDTO;
import dtos.PaymentDTOGET;
import jakarta.enterprise.context.RequestScoped;
import model.Address;
import model.Customer;
import model.Payment;

import java.util.HashMap;
import java.util.Map;

@RequestScoped
public class DTOEntityUtil {


    public static AddressDTO createAddressDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getAddressId());
        dto.setAddress(address.getAddress());
        dto.setAddress2(address.getAddress2() == null ? "" : address.getAddress2());
        dto.setDistrict(address.getDistrict());
        dto.setPhone(address.getPhone());
        dto.setPostalCode(address.getPostalCode());
        dto.setCity(address.getCity().getCity());
        dto.setCountry(address.getCity().getCountry().getCountry());
        return dto;
    }

    public static Address createAddressFROMDTO(AddressDTO addressDTO) {
        Address address = new Address();
        address.setAddress(addressDTO.getAddress());
        address.setAddress2(addressDTO.getAddress2().equals("") ? "" : addressDTO.getAddress2());
        address.setDistrict(addressDTO.getDistrict());
        address.setPhone(addressDTO.getPhone());
        address.setPostalCode(addressDTO.getPostalCode());
        return address;
    }

    public static CustomerDTO createCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getCustomerId());
        Map<String, String> addressHref = new HashMap<>();
        addressHref.put("href", Hrefs.CUSTOMER.getHref()!=null?Hrefs.CUSTOMER.getHref() + "addresses/" + customer.getAddress().getAddressId():"");
        customerDTO.setAddress(addressHref);

        customerDTO.setActive(customer.getActive());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setCreateDate(customer.getCreateDate());
        customerDTO.setActivebool(customer.isActivebool());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setFirstName(customer.getFirstName());

        Map<String, String> storeHref = new HashMap<>();
        storeHref.put("href", Hrefs.STORE.getHref()!=null?Hrefs.STORE.getHref() + "stores/" + customer.getStoreId():"");
        customerDTO.setStore(storeHref);

        return customerDTO;
    }

    public static PaymentDTOGET createPaymentDTO(Payment payment) {
        PaymentDTOGET paymentDTOGET = new PaymentDTOGET();
        paymentDTOGET.setId(payment.getPaymentId());
        paymentDTOGET.setAmount(payment.getAmount());

        Map<String, String> customerHref = new HashMap<>();
        customerHref.put("href", Hrefs.CUSTOMER.getHref()!=null?Hrefs.CUSTOMER.getHref() + "customers/" + payment.getCustomerByCustomerId().getCustomerId():"");
        paymentDTOGET.setCustomer(customerHref);

        Map<String, String> staffHref = new HashMap<>();
        staffHref.put("href", Hrefs.STORE.getHref()!=null?Hrefs.STORE.getHref() + "staff/" + payment.getStaffId():"");
        paymentDTOGET.setStaff(staffHref);

        Map<String, String> rentalHref = new HashMap<>();
        rentalHref.put("href", Hrefs.STORE.getHref()!=null?Hrefs.STORE.getHref() + "rentals/" + payment.getRentalId():"");
        paymentDTOGET.setRental(rentalHref);

        return paymentDTOGET;
    }
}
