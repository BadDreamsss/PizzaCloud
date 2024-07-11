package sia.pizzacloud.data;

import jakarta.persistence.*;
import jakarta.persistence.metamodel.Type;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "ingredient")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Ingredient {

    @Id
    private final String id;
    private final String name;
    @Enumerated(EnumType.STRING)
    private final Type type;

    public enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Ingredient> pizza_ingredient;

}