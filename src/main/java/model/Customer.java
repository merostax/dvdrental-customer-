package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Customer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "customer_id", nullable = false)
    private int customerId;
    @Column(name = "store_id", nullable = false)
    private int storeId;
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;
    @Column(name = "email", nullable = true, length = 50)
    private String email;
    @Column(name = "activebool", nullable = false)
    private boolean activebool;
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    @Column(name = "last_update", nullable = true)
    private Timestamp lastUpdate;
    @Column(name = "active", nullable = true)
    private Integer active;
    @ManyToOne
    @JoinColumn(name = "address_id") // Specify the foreign key column
    private Address address;
}
