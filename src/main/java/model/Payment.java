package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@EqualsAndHashCode
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
}
