package dtos;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

import java.sql.Date;
import java.util.Map;

@JsonbPropertyOrder({"id", "active", "activebool", "createDate", "email", "firstName", "lastName", "store", "address"})
public class CustomerDTO {
    private int id;
    private int active;
    private boolean activebool;
    private Date createDate;
    private String email;
    private String firstName;
    private String lastName;
    private Map store;
    private Map address;

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getActive() {
        return this.active;
    }

    public void setActive(final int active) {
        this.active = active;
    }

    public boolean isActivebool() {
        return this.activebool;
    }

    public void setActivebool(final boolean activebool) {
        this.activebool = activebool;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Map getStore() {
        return this.store;
    }

    public void setStore(final Map store) {
        this.store = store;
    }

    public Map getAddress() {
        return this.address;
    }

    public void setAddress(final Map address) {
        this.address = address;
    }
}
