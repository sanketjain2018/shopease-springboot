package in.sj.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class CheckoutController {

    // SHOW CHECKOUT PAGE
    @GetMapping("/checkout")
    public String checkoutPage() {
        return "checkout";
    }

    // PLACE ORDER
    @PostMapping("/checkout")
    public String placeOrder() {

        return "redirect:/user/order-success";
    }
}
