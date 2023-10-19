package dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private String address;
    private String address2;
    private String district;
    private String postalCode;
    private String phone;
    private String city;
    private String country;
}
