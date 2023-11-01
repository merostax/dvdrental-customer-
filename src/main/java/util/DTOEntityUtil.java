package util;

import dtos.AddressDTO;
import dtos.CustomerDTO;
import dtos.PaymentDTOGET;
import jakarta.enterprise.context.RequestScoped;
import model.Address;
import model.Customer;
import model.Payment;

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
        customerDTO.setAddress(Hrefs.CUSTOMER.getHref()+"addresses/"+customer.getAddress().getAddressId());
        customerDTO.setActive(customer.getActive());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setCreateDate(customer.getCreateDate());
        customerDTO.setActivebool(customer.isActivebool());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setStore(Hrefs.STORE.getHref()+"stores/"+customer.getStoreId());
        return customerDTO;
    }

    public static PaymentDTOGET createPaymentDTO(Payment payment) {
        PaymentDTOGET paymentDTOGET= new PaymentDTOGET();
        paymentDTOGET.setId(payment.getPaymentId());
        paymentDTOGET.setAmount(payment.getAmount());
        paymentDTOGET.setCustomer(Hrefs.CUSTOMER.getHref()+"customers/"+payment.getCustomerByCustomerId().getCustomerId());
        paymentDTOGET.setStaff(Hrefs.STORE.getHref()+"staff/"+payment.getStaffId());
        paymentDTOGET.setRental(Hrefs.STORE.getHref()+"rentals/"+payment.getRentalId());
        return paymentDTOGET;
    }
}
