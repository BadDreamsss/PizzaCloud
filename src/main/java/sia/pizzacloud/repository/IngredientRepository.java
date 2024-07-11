package sia.pizzacloud.repository;

import org.springframework.data.repository.CrudRepository;
import sia.pizzacloud.data.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
