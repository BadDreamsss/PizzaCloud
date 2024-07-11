package sia.pizzacloud.data;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "pizza_order")
public class PizzaOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date placedAt = new Date();

    @NotBlank(message = "Name is required")
    private String delivery_name;
    @NotBlank(message = "Street is required")
    private String delivery_street;
    @NotBlank(message = "City is required")
    private String delivery_city;
    @NotBlank(message = "State is required")
    private String delivery_state;
    @NotBlank(message = "Zip code is required")
    private String delivery_zip;
    @CreditCardNumber(message = "Not a valid credit card number")
    private String cc_number;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message = "Must be formatted MM/YY")
    private String cc_expiration;
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String cc_cvv;

    @OneToMany(mappedBy = "pizza_order_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pizza> pizzas = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_table_id", referencedColumnName = "id")
    private UserTable userTableId;

    public void addPizza(Pizza pizza) {
        this.pizzas.add(pizza);
        pizza.setPizza_order_id(PizzaOrder.this);
    }

}
