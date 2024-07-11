package sia.pizzacloud.controller;

import jakarta.validation.Valid;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sia.pizzacloud.data.Ingredient.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import sia.pizzacloud.data.Ingredient;
import sia.pizzacloud.data.Pizza;
import sia.pizzacloud.data.PizzaOrder;
import sia.pizzacloud.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/design")
@SessionAttributes("pizzaOrder")
public class DesignPizzaController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public DesignPizzaController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @ModelAttribute
    public void addIngredientToModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        Type[] types = Ingredient.Type.values();
        for(Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "pizzaOrder")
    public PizzaOrder order(){
        return new PizzaOrder();
    }

    @ModelAttribute(name = "pizza")
    public Pizza pizza(){
        return new Pizza();
    }

    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    @PostMapping
    public String processPizza(@Valid Pizza pizza, Errors errors, @ModelAttribute("pizzaOrder") PizzaOrder pizzaOrder){
        if(errors.hasErrors()){
            return "design";
        }

        pizzaOrder.addPizza(pizza);

        return "redirect:/order/current";

    }

}
