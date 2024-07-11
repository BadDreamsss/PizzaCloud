package sia.pizzacloud.repository;

import org.springframework.data.repository.CrudRepository;
import sia.pizzacloud.data.UserTable;

public interface UserRepository extends CrudRepository<UserTable, Long> {
    UserTable findByUsername(String username);
}
