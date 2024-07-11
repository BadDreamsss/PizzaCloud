package sia.pizzacloud.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 2, message = "Имя должно содержать хотя бы 2 символа")
    private String name;

    private Date created_at = new Date();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pizza_order_id", referencedColumnName = "id")
    private PizzaOrder pizza_order_id;

    @OneToMany(mappedBy = "pizza_id", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<PizzaIngredient> pizzaIngredients;

    @ManyToMany
    private List<Ingredient> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }

}
