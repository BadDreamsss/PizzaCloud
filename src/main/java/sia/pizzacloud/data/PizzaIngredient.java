package sia.pizzacloud.data;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pizza_ingredients")
public class PizzaIngredient {

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "pizza_id", referencedColumnName = "id")
    private Pizza pizza_id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private Ingredient ingredient_id;

}
