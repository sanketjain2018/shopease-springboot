package in.sj.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import in.sj.service.CartService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user/cart")
@RequiredArgsConstructor
public class CartController {

    private static final Logger log =
            LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    // ================= VIEW CART =================
    @GetMapping
    public String viewCart(Model model, Principal principal) {

        String username = principal.getName();
        log.info("VIEW CART | user={}", username);

        model.addAttribute("cartItems",
                cartService.getCartItems(username));

        model.addAttribute("total",
                cartService.calculateTotal(username));

        return "cart";
    }

    // ================= ADD TO CART =================
    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId,
                            Principal principal) {

        String username = principal.getName();
        log.info("ADD TO CART | user={} | productId={}", username, productId);

        cartService.addToCart(username, productId);
        return "redirect:/user/cart";
    }

    // ================= INCREASE QUANTITY =================
    @PostMapping("/increase/{productId}")
    public String increaseQty(@PathVariable Long productId,
                              Principal principal) {

        String username = principal.getName();
        log.info("INCREASE QTY | user={} | productId={}", username, productId);

        cartService.changeQuantity(username, productId, 1);
        return "redirect:/user/cart";
    }

    // ================= DECREASE QUANTITY =================
    @PostMapping("/decrease/{productId}")
    public String decreaseQty(@PathVariable Long productId,
                              Principal principal) {

        String username = principal.getName();
        log.info("DECREASE QTY | user={} | productId={}", username, productId);

        cartService.changeQuantity(username, productId, -1);
        return "redirect:/user/cart";
    }

    // ================= REMOVE ITEM =================
    @PostMapping("/remove/{productId}")
    public String removeItem(@PathVariable Long productId,
                             Principal principal) {

        String username = principal.getName();
        log.info("REMOVE ITEM | user={} | productId={}", username, productId);

        cartService.removeItem(username, productId);
        return "redirect:/user/cart";
    }

    // ================= CLEAR CART =================
    @PostMapping("/clear")
    public String clearCart(Principal principal) {

        String username = principal.getName();
        log.warn("CLEAR CART | user={}", username);

        cartService.clearCart(username);
        return "redirect:/user/cart";
    }
}
