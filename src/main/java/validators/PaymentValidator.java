package validators;

import java.math.BigDecimal;

import clienTargetRepository.StoreServiceClientProvider;
import dtos.PaymentDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.CustomerRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.regex.Pattern;
@ApplicationScoped
public class PaymentValidator {

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private StoreServiceClientProvider storeServiceClientProvider;
    public boolean isValidPayment(PaymentDTO paymentDTO) {
        return isValidAmount(paymentDTO.getAmount()) &&
                isValidCustomer( paymentDTO.getCustomer()) &&
                isValidStaff(paymentDTO.getStaff()) &&
                isValidPaymentDate(paymentDTO.getDate()) &&
                isValidRental( paymentDTO.getRental());
    }

    public boolean isValidAmount(@DecimalMin("0.0") BigDecimal amount) {
        return true;
    }

    public boolean isValidCustomer(@Min(1) int customerId) {
        return customerRepository.getCustomerById(customerId) != null;
    }

    public boolean isValidRental(@Min(1) int rentalId) {
        Response storeResponse = this.storeServiceClientProvider.getStoreServiceTarget()
                .path("rentals")
                .path(String.valueOf(rentalId))
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (storeResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            return true;
        } else {
            throw new NotFoundException("Rental with ID " + rentalId + " not found.");
        }
    }

    public boolean isValidStaff(@Min(1) int staffId) {
        Response storeResponse = this.storeServiceClientProvider.getStoreServiceTarget()
                .path("staff")
                .path(String.valueOf(staffId))
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (storeResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            return true;
        } else {
            throw new NotFoundException("Staff with ID " + staffId + " not found.");
        }
    }

    public boolean isValidPaymentDate(String paymentDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(paymentDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}

