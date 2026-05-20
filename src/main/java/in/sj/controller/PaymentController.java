package in.sj.controller;

import in.sj.dto.PaymentOrderResponse;
import in.sj.dto.PaymentVerificationRequest;
import in.sj.service.CartService;
import in.sj.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/user/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final CartService cartService;

    // ================= CHECKOUT PAGE =================
    @GetMapping("/checkout")
    public String checkout(Model model,
                           @AuthenticationPrincipal UserDetails userDetails,
                           RedirectAttributes redirectAttributes) {

        // ── null guard ──
        if (userDetails == null) {
            log.warn("CHECKOUT | Unauthenticated access");
            return "redirect:/login";
        }

        String username = userDetails.getUsername();
        log.info("CHECKOUT PAGE | user={}", username);

        // ── empty cart guard ──
        if (cartService.getCartItems(username).isEmpty()) {
            log.warn("CHECKOUT | Empty cart | user={}", username);
            redirectAttributes.addFlashAttribute("error", "Your cart is empty!");
            return "redirect:/user/cart";
        }

        try {
            // creates Razorpay order via API and returns orderId, amount, currency, keyId
            PaymentOrderResponse order = paymentService.createOrder(username);

            model.addAttribute("order",     order);
            model.addAttribute("cartItems", cartService.getCartItems(username));
            model.addAttribute("total",     cartService.calculateTotal(username));

            log.info("CHECKOUT | Razorpay order created | orderId={} | user={}",
                     order.getOrderId(), username);

        } catch (Exception e) {
            log.error("CHECKOUT | Failed to create Razorpay order | user={} | error={}",
                      username, e.getMessage());
            redirectAttributes.addFlashAttribute("error",
                    "Payment session could not be created. Please try again.");
            return "redirect:/user/cart";
        }

        return "checkout";
    }

    // ================= VERIFY PAYMENT (called by Razorpay handler) =================
    @PostMapping("/verify")
    public String verifyPayment(@ModelAttribute PaymentVerificationRequest req,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {

        // ── null guard ──
        if (userDetails == null) {
            log.warn("VERIFY PAYMENT | Unauthenticated access");
            return "redirect:/login";
        }

        String username = userDetails.getUsername();
        log.info("VERIFY PAYMENT | user={} | orderId={}", username, req.getRazorpayOrderId());

        try {
            // ── HMAC-SHA256 signature check ──
            boolean isValid = paymentService.verifySignature(req);

            if (isValid) {
                log.info("PAYMENT SUCCESS | user={} | paymentId={}",
                         username, req.getRazorpayPaymentId());

                cartService.clearCart(username);

                // TODO: Save order to DB here (OrderService.saveOrder(...))

                redirectAttributes.addFlashAttribute("success",
                        "🎉 Payment successful! Your order has been placed.");
                return "redirect:/user/orders";

            } else {
                log.warn("PAYMENT FAILED | Invalid signature | user={} | orderId={}",
                         username, req.getRazorpayOrderId());
                redirectAttributes.addFlashAttribute("error",
                        "Payment verification failed. Please contact support.");
                return "redirect:/user/cart";
            }

        } catch (Exception e) {
            log.error("VERIFY PAYMENT | Exception | user={} | error={}", username, e.getMessage());
            redirectAttributes.addFlashAttribute("error",
                    "Something went wrong during payment. Please try again.");
            return "redirect:/user/cart";
        }
    }
}