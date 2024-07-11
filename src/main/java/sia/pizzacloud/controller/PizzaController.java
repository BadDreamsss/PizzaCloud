package sia.pizzacloud.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sia.pizzacloud.data.Pizza;
import sia.pizzacloud.repository.PizzaRepository;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/pizzas",
        produces = {"application/josn", "text/xml"})
@CrossOrigin(origins = {"http://pizzaloud:8080", "http://pizzacloud.com"})
public class PizzaController {

    private PizzaRepository pizzaRepository;

    public PizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping(params = "recent")
    public Iterable<Pizza> recentPizzas() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return pizzaRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> PizzaById(@PathVariable("id") Long id) {
        Optional<Pizza> opPizza = pizzaRepository.findById(id);
        if(opPizza.isPresent()) {
            return new ResponseEntity<>(opPizza.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Pizza postPizza(@RequestBody Pizza pizza){
        return pizzaRepository.save(pizza);
    }

}
