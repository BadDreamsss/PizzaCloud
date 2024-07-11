package sia.pizzacloud.controller;

import jakarta.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import sia.pizzacloud.component.OrderProps;
import sia.pizzacloud.data.PizzaOrder;
import sia.pizzacloud.data.UserTable;
import sia.pizzacloud.repository.OrderRepository;
import sia.pizzacloud.service.UserUtils;

@Controller
@RequestMapping(value = "/orders", method = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.POST, RequestMethod.DELETE
})
@SessionAttributes(value = "pizzaOrder")
public class OrderController {

    private OrderProps props;

    private OrderRepository orderRepository;
    private UserUtils userUtils;

    public OrderController(OrderRepository orderRepository, UserUtils userUtils, OrderProps props) {
        this.orderRepository = orderRepository;
        this.userUtils = userUtils;
        this.props = props;
    }

    @ModelAttribute("userTable")
    public UserTable userTable(){
        return (UserTable) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/ordersInfo")
    public String getOrders(){
        return "orderInfo";
    }

    @GetMapping(value = {
            "/current", "/"
    })
    public String orderForm(){
        return "orderForm";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteAllOrders(){
        orderRepository.deleteAll();
    }

    @PostMapping("/process")
    public String processOrder(@Valid PizzaOrder pizzaOrder, Errors errors, SessionStatus status,
                               @AuthenticationPrincipal UserTable userTable){
        if(errors.hasErrors()){
            return "orderForm";
        }

        pizzaOrder.setUserTableId(userTable);

        orderRepository.save(pizzaOrder);
        status.setComplete();

        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal UserTable user, Model model){

        Pageable pageable = PageRequest.of(0, props.getPageSize());
        model.addAttribute("oders",
                orderRepository.findByUserTableIdOrderByPlacedAtDesc(user, pageable));

        return "orderList";
    }

    @PatchMapping(path="/{orderId}", consumes="application/json")
    public PizzaOrder patchOrder(@PathVariable("orderId") Long orderId,
                                @RequestBody PizzaOrder patch) {
        PizzaOrder order = orderRepository.findById(orderId).get();
        if (patch.getDelivery_name() != null) {
            order.setDelivery_name(patch.getDelivery_name());
        }
        if (patch.getDelivery_street() != null) {
            order.setDelivery_street(patch.getDelivery_street());
        }
        if (patch.getDelivery_city() != null) {
            order.setDelivery_city(patch.getDelivery_city());
        }
        if (patch.getDelivery_state() != null) {
            order.setDelivery_state(patch.getDelivery_state());
        }
        if (patch.getDelivery_zip() != null) {
            order.setDelivery_zip(patch.getDelivery_zip());
        }
        if (patch.getCc_number() != null) {
            order.setCc_number(patch.getCc_number());
        }
        if (patch.getCc_expiration() != null) {
            order.setCc_expiration(patch.getCc_expiration());
        }
        if (patch.getCc_cvv() != null) {
            order.setCc_cvv(patch.getCc_cvv());
        }
        return orderRepository.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {}
    }

}
