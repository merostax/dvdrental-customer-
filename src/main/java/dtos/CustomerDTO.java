package dtos;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@JsonbPropertyOrder({"id", "active", "activebool", "createDate", "email", "firstName", "lastName", "store", "address"})
public class CustomerDTO {
    private int id;
    private int active;
    private boolean activebool;
    private Date createDate;
    private String email;
    private String firstName;
    private String lastName;
    private String store;
    private String address;
}
