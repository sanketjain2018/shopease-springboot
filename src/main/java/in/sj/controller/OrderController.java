package in.sj.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.sj.entity.Order;
import in.sj.repository.OrderRepository;
import in.sj.service.OrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user/orders")
@RequiredArgsConstructor
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	private final OrderRepository orderRepository;
	private final OrderService orderService;

	// ================= MY ORDERS PAGE =================
	@GetMapping
	public String orders(Model model, Principal principal) {

		String username = principal.getName();
		log.info("VIEW ORDERS | user={}", username);

		List<Order> orders = orderRepository.findByUsername(username);
		log.debug("ORDERS FETCHED | user={} | count={}", username, orders.size());

		model.addAttribute("orders", orders);
		return "orders";
	}

	// ================= ORDER DETAILS PAGE =================

	@GetMapping("/{id}")
	public String orderDetails(@PathVariable Long id, Model model, Principal principal) {

		String username = principal.getName();
		log.info("VIEW ORDER DETAILS | user={} | orderId={}", username, id);

		Order order = orderRepository.findByIdWithItems(id).orElseThrow(() -> new RuntimeException("Order not found"));

		if (!order.getUsername().equals(username)) {
			throw new RuntimeException("Access denied");
		}

		model.addAttribute("order", order);
		return "order-details";
	}

	// ================= PLACE ORDER =================

	@PostMapping("/place")
	public String placeOrder(Principal principal) {

		String username = principal.getName();
		log.info("PLACE ORDER REQUEST | user={}", username);

		orderService.placeOrder(username);

		// Show success page
		return "order-success";
	}

	// ================= CANCEL ORDER =================
	@PostMapping("/cancel/{id}")
	public String cancelOrder(@PathVariable Long id, Principal principal) {

		String username = principal.getName();
		log.info("CANCEL ORDER REQUEST | user={} | orderId={}", username, id);

		orderService.cancelOrder(id, username);
		return "redirect:/user/orders";
	}

	// ================= REORDER =================
	@PostMapping("/reorder/{id}")
	public String reorder(@PathVariable Long id, Principal principal) {

		String username = principal.getName();
		log.info("REORDER REQUEST | user={} | orderId={}", username, id);

		orderService.reorder(id, username);
		return "redirect:/user/cart";
	}
}
