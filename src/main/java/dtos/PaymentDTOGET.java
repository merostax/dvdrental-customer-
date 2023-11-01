package dtos;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonbPropertyOrder({"id", "amount", "staff", "rental", "customer"})
public class PaymentDTOGET {
    private int id;
    private BigDecimal amount;
    private String rental;
    private String customer;
    private String staff;
}
