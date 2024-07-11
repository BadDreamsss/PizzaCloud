package sia.pizzacloud.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import sia.pizzacloud.data.PizzaOrder;
import sia.pizzacloud.data.UserTable;

import java.util.List;

public interface OrderRepository extends CrudRepository<PizzaOrder, Long> {

    List<PizzaOrder> findByUserTableIdOrderByPlacedAtDesc(UserTable userTable, Pageable pageable);

}
