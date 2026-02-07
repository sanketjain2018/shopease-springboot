package in.sj.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.sj.service.CartService;
import in.sj.service.OrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final OrderService orderService;
    private final CartService cartService;

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    // ================= USER DASHBOARD =================
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        String username = principal.getName();

        long totalOrders = orderService.countOrdersByUsername(username);
        long cartItems = cartService.countCartItemsByUsername(username);

        model.addAttribute("username", username);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("cartItems", cartItems);

        return "user-dashboard";
    }
}
