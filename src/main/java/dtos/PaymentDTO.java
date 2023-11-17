package dtos;


import java.math.BigDecimal;
public class PaymentDTO {
    private BigDecimal amount;
    private int rental;
    private int customer;
    private int staff;
    private String date;

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public int getRental() {
        return this.rental;
    }

    public void setRental(final int rental) {
        this.rental = rental;
    }

    public int getCustomer() {
        return this.customer;
    }

    public void setCustomer(final int customer) {
        this.customer = customer;
    }

    public int getStaff() {
        return this.staff;
    }

    public void setStaff(final int staff) {
        this.staff = staff;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(final String date) {
        this.date = date;
    }
}
