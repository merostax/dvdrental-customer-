package dtos;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonbPropertyOrder({"id", "amount", "staff", "rental", "customer"})
public class PaymentDTOGET {
    private int id;
    private BigDecimal amount;
    private Map rental;
    private Map customer;
    private Map staff;
}
