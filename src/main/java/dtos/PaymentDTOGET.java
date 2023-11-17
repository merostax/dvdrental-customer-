package dtos;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

import java.math.BigDecimal;
import java.util.Map;

@JsonbPropertyOrder({"id", "amount", "staff", "rental", "customer"})
public class PaymentDTOGET {
    private int id;
    private BigDecimal amount;
    private Map rental;
    private Map customer;
    private Map staff;

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public Map getRental() {
        return this.rental;
    }

    public void setRental(final Map rental) {
        this.rental = rental;
    }

    public Map getCustomer() {
        return this.customer;
    }

    public void setCustomer(final Map customer) {
        this.customer = customer;
    }

    public Map getStaff() {
        return this.staff;
    }

    public void setStaff(final Map staff) {
        this.staff = staff;
    }
}
