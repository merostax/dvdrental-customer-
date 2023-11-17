package model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class Payment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "payment_id", nullable = false)
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerByCustomerId;

    @Column(name = "staff_id", nullable = false)
    private int staffId;

    @Column(name = "rental_id", nullable = false)
    private int rentalId;

    @Column(name = "amount", nullable = false, precision = 2)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private Timestamp paymentDate;
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        paymentDate = new Timestamp(System.currentTimeMillis());
    }

    public int getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(final int paymentId) {
        this.paymentId = paymentId;
    }

    public Customer getCustomerByCustomerId() {
        return this.customerByCustomerId;
    }

    public void setCustomerByCustomerId(final Customer customerByCustomerId) {
        this.customerByCustomerId = customerByCustomerId;
    }

    public int getStaffId() {
        return this.staffId;
    }

    public void setStaffId(final int staffId) {
        this.staffId = staffId;
    }

    public int getRentalId() {
        return this.rentalId;
    }

    public void setRentalId(final int rentalId) {
        this.rentalId = rentalId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getPaymentDate() {
        return this.paymentDate;
    }

    public void setPaymentDate(final Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }
}
