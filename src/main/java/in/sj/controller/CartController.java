/*
 * package in.sj.controller;
 * 
 * import java.security.Principal;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.*;
 * 
 * import in.sj.service.CartService; import lombok.RequiredArgsConstructor;
 * 
 * @Controller
 * 
 * @RequestMapping("/user/cart")
 * 
 * @RequiredArgsConstructor public class CartController {
 * 
 * private static final Logger log =
 * LoggerFactory.getLogger(CartController.class);
 * 
 * private final CartService cartService;
 * 
 * // ================= VIEW CART =================
 * 
 * @GetMapping public String viewCart(Model model, Principal principal) {
 * 
 * String username = principal.getName(); log.info("VIEW CART | user={}",
 * username);
 * 
 * model.addAttribute("cartItems", cartService.getCartItems(username));
 * 
 * model.addAttribute("total", cartService.calculateTotal(username));
 * 
 * return "cart"; }
 * 
 * // ================= ADD TO CART =================
 * 
 * @PostMapping("/add/{productId}") public String addToCart(@PathVariable Long
 * productId, Principal principal) {
 * 
 * String username = principal.getName();
 * log.info("ADD TO CART | user={} | productId={}", username, productId);
 * 
 * cartService.addToCart(username, productId); return "redirect:/user/cart"; }
 * 
 * // ================= INCREASE QUANTITY =================
 * 
 * @PostMapping("/increase/{productId}") public String increaseQty(@PathVariable
 * Long productId, Principal principal) {
 * 
 * String username = principal.getName();
 * log.info("INCREASE QTY | user={} | productId={}", username, productId);
 * 
 * cartService.changeQuantity(username, productId, 1); return
 * "redirect:/user/cart"; }
 * 
 * // ================= DECREASE QUANTITY =================
 * 
 * @PostMapping("/decrease/{productId}") public String decreaseQty(@PathVariable
 * Long productId, Principal principal) {
 * 
 * String username = principal.getName();
 * log.info("DECREASE QTY | user={} | productId={}", username, productId);
 * 
 * cartService.changeQuantity(username, productId, -1); return
 * "redirect:/user/cart"; }
 * 
 * // ================= REMOVE ITEM =================
 * 
 * @PostMapping("/remove/{productId}") public String removeItem(@PathVariable
 * Long productId, Principal principal) {
 * 
 * String username = principal.getName();
 * log.info("REMOVE ITEM | user={} | productId={}", username, productId);
 * 
 * cartService.removeItem(username, productId); return "redirect:/user/cart"; }
 * 
 * // ================= CLEAR CART =================
 * 
 * @PostMapping("/clear") public String clearCart(Principal principal) {
 * 
 * String username = principal.getName(); log.warn("CLEAR CART | user={}",
 * username);
 * 
 * cartService.clearCart(username); return "redirect:/user/cart"; } }
 */


// Above Main Code 

package in.sj.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.sj.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;          // ✅ Fix #1: Use Lombok @Slf4j

@Slf4j                                      // ✅ Fix #1: Replaces manual Logger declaration
@Controller
@RequestMapping("/user/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // ================= VIEW CART =================
    @GetMapping
    public String viewCart(Model model,
                           @AuthenticationPrincipal UserDetails userDetails) { // ✅ Fix #3

        // ✅ Fix #2: Null guard for principal
        if (userDetails == null) {
            log.warn("VIEW CART | Unauthenticated access attempt");
            return "redirect:/login";
        }

        String username = userDetails.getUsername();
        log.info("VIEW CART | user={}", username);

        try {                                                                   // ✅ Fix #4
            model.addAttribute("cartItems", cartService.getCartItems(username));
            model.addAttribute("total", cartService.calculateTotal(username));
        } catch (Exception e) {
            log.error("VIEW CART failed | user={} | error={}", username, e.getMessage());
            model.addAttribute("error", "Unable to load cart. Please try again.");
        }

        return "cart";
    }

    // ================= ADD TO CART =================
    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId,
                            @AuthenticationPrincipal UserDetails userDetails, // ✅ Fix #3
                            RedirectAttributes redirectAttributes) {           // ✅ Fix #5

        if (userDetails == null) return "redirect:/login";                     // ✅ Fix #2

        String username = userDetails.getUsername();
        log.info("ADD TO CART | user={} | productId={}", username, productId);

        try {                                                                   // ✅ Fix #4
            cartService.addToCart(username, productId);
            redirectAttributes.addFlashAttribute("success", "Item added to cart!"); // ✅ Fix #5
        } catch (Exception e) {
            log.error("ADD TO CART failed | user={} | productId={} | error={}",
                      username, productId, e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Could not add item: " + e.getMessage());
        }

        return "redirect:/user/cart";
    }

    // ================= INCREASE QUANTITY =================
    @PostMapping("/increase/{productId}")
    public String increaseQty(@PathVariable Long productId,
                              @AuthenticationPrincipal UserDetails userDetails, // ✅ Fix #3
                              RedirectAttributes redirectAttributes) {

        if (userDetails == null) return "redirect:/login";                      // ✅ Fix #2

        String username = userDetails.getUsername();
        log.info("INCREASE QTY | user={} | productId={}", username, productId);

        try {                                                                    // ✅ Fix #4
            cartService.changeQuantity(username, productId, 1);
        } catch (Exception e) {
            log.error("INCREASE QTY failed | user={} | productId={} | error={}",
                      username, productId, e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Could not update quantity.");
        }

        return "redirect:/user/cart";
    }

    // ================= DECREASE QUANTITY =================
    @PostMapping("/decrease/{productId}")
    public String decreaseQty(@PathVariable Long productId,
                              @AuthenticationPrincipal UserDetails userDetails, // ✅ Fix #3
                              RedirectAttributes redirectAttributes) {

        if (userDetails == null) return "redirect:/login";                      // ✅ Fix #2

        String username = userDetails.getUsername();
        log.info("DECREASE QTY | user={} | productId={}", username, productId);

        try {                                                                    // ✅ Fix #4, #6
            // ✅ Fix #6: Only decrease if quantity > 1, else remove the item
            int currentQty = cartService.getItemQuantity(username, productId);
            if (currentQty <= 1) {
                cartService.removeItem(username, productId);
                redirectAttributes.addFlashAttribute("info", "Item removed from cart.");
            } else {
                cartService.changeQuantity(username, productId, -1);
            }
        } catch (Exception e) {
            log.error("DECREASE QTY failed | user={} | productId={} | error={}",
                      username, productId, e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Could not update quantity.");
        }

        return "redirect:/user/cart";
    }

    // ================= REMOVE ITEM =================
    @PostMapping("/remove/{productId}")
    public String removeItem(@PathVariable Long productId,
                             @AuthenticationPrincipal UserDetails userDetails, // ✅ Fix #3
                             RedirectAttributes redirectAttributes) {

        if (userDetails == null) return "redirect:/login";                     // ✅ Fix #2

        String username = userDetails.getUsername();
        log.info("REMOVE ITEM | user={} | productId={}", username, productId);

        try {                                                                   // ✅ Fix #4
            cartService.removeItem(username, productId);
            redirectAttributes.addFlashAttribute("success", "Item removed from cart."); // ✅ Fix #5
        } catch (Exception e) {
            log.error("REMOVE ITEM failed | user={} | productId={} | error={}",
                      username, productId, e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Could not remove item.");
        }

        return "redirect:/user/cart";
    }

    // ================= CLEAR CART =================
    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal UserDetails userDetails, // ✅ Fix #3
                            RedirectAttributes redirectAttributes) {

        if (userDetails == null) return "redirect:/login";                    // ✅ Fix #2

        String username = userDetails.getUsername();
        log.warn("CLEAR CART | user={}", username);

        try {                                                                  // ✅ Fix #4
            cartService.clearCart(username);
            redirectAttributes.addFlashAttribute("success", "Cart cleared successfully.");
        } catch (Exception e) {
            log.error("CLEAR CART failed | user={} | error={}", username, e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Could not clear cart.");
        }

        return "redirect:/user/products";                                     // ✅ Fix #7: Better UX
    }
}



