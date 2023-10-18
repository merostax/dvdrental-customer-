package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Address {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "address_id", nullable = false)
    private int addressId;
    @Column(name = "address", nullable = false, length = 50)
    private String address;
    @Column(name = "address2", nullable = true, length = 50)
    private String address2;
    @Column(name = "district", nullable = false, length = 20)
    private String district;
    @Column(name = "postal_code", nullable = true, length = 10)
    private String postalCode;
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
    @Column(name = "last_update", nullable = false)
    private Timestamp lastUpdate;
    @PrePersist
    protected void onCreate() {
        lastUpdate = new Timestamp(System.currentTimeMillis());
    }

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

}
