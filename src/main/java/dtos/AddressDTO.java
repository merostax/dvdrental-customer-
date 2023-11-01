package dtos;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonbPropertyOrder({"id", "address", "address2", "district", "phone", "postalCode", "city", "country"})
public class AddressDTO {
    private int id;
    private String address;
    private String address2;
    private String district;
    private String phone;
    private String postalCode;
    private String city;
    private String country;
}
