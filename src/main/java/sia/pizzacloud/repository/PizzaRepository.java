package sia.pizzacloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import sia.pizzacloud.data.Pizza;

public interface PizzaRepository extends CrudRepository<Pizza,Long>,
        PagingAndSortingRepository<Pizza,Long> {

}
