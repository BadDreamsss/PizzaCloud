package sia.pizzacloud.service;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import sia.pizzacloud.data.UserTable;

import java.util.List;

@Service
public class UserUtils {
    private EntityManager entityManager;

    public UserUtils(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<UserTable> buildUsers(){
        return entityManager.createQuery("SELECT u FROM UserTable u", UserTable.class).getResultList();
    }
}
