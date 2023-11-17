package model;


import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class Address {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "address_id", nullable = false)
    private int addressId;
    @Column(name = "address", nullable = false, length = 50)
    private String  address;
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
    @PreUpdate
    protected void onUpdate() {
        lastUpdate = new Timestamp(System.currentTimeMillis());
    }

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public int getAddressId() {
        return this.addressId;
    }

    public void setAddressId(final int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(final String district) {
        this.district = district;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public Timestamp getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(final Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(final City city) {
        this.city = city;
    }
}
