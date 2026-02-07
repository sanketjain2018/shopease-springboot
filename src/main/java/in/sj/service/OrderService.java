package in.sj.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import in.sj.entity.CartItem;
import in.sj.entity.Order;
import in.sj.entity.OrderItem;
import in.sj.entity.OrderStatus;
import in.sj.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderService.class);

	private final OrderRepository orderRepository;
	private final CartService cartService;

	// ================= PLACE ORDER =================

	@Transactional
	public void placeOrder(String username) {

		log.info("PLACE ORDER STARTED | user={}", username);

		List<CartItem> cartItems = cartService.getCartItems(username);
		log.debug("CART ITEMS FETCHED | user={} | count={}", username, cartItems.size());

		if (cartItems.isEmpty()) {
			log.warn("PLACE ORDER FAILED | EMPTY CART | user={}", username);
			throw new RuntimeException("Cart is empty");
		}

		Order order = new Order();
		order.setUsername(username);
		order.setOrderDate(LocalDateTime.now());
		order.setStatus(OrderStatus.PLACED);

		List<OrderItem> orderItems = new ArrayList<>();
		double totalAmount = 0;

		for (CartItem cartItem : cartItems) {

			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPrice(cartItem.getProduct().getPrice());

			orderItems.add(orderItem);

			double itemTotal = cartItem.getProduct().getPrice() * cartItem.getQuantity();
			totalAmount += itemTotal;

			log.debug("ORDER ITEM ADDED | product={} | qty={} | itemTotal={}", cartItem.getProduct().getName(),
					cartItem.getQuantity(), itemTotal);
		}

		order.setItems(orderItems);
		order.setTotalAmount(totalAmount);

		orderRepository.save(order);
		log.info("ORDER SAVED | user={} | orderId={} | total={}", username, order.getId(), totalAmount);

		// ✅ CLEAR CART ONLY HERE cartService.clearCart(username);
		log.info("CART CLEARED AFTER ORDER | user={}", username);
	}

	// ================= CANCEL ORDER =================
	public void cancelOrder(Long orderId, String username) {

		log.info("CANCEL ORDER REQUEST | user={} | orderId={}", username, orderId);

		Order order = orderRepository.findById(orderId).orElseThrow(() -> {
			log.warn("ORDER NOT FOUND | orderId={}", orderId);
			return new RuntimeException("Order not found");
		});

		if (!order.getUsername().equals(username)) {
			log.warn("CANCEL DENIED | user={} | owner={} | orderId={}", username, order.getUsername(), orderId);
			throw new RuntimeException("You are not allowed to cancel this order");
		}

		if (order.getStatus() == OrderStatus.SHIPPED) {
			log.warn("CANCEL FAILED | ORDER SHIPPED | orderId={}", orderId);
			throw new RuntimeException("Order already shipped");
		}

		order.setStatus(OrderStatus.CANCELLED);
		orderRepository.save(order);

		log.info("ORDER CANCELLED | user={} | orderId={}", username, orderId);
	}

	// ================= REORDER =================
	public void reorder(Long orderId, String username) {

		log.info("REORDER STARTED | user={} | orderId={}", username, orderId);

		Order order = orderRepository.findById(orderId).orElseThrow(() -> {
			log.warn("ORDER NOT FOUND FOR REORDER | orderId={}", orderId);
			return new RuntimeException("Order not found");
		});

		if (!order.getUsername().equals(username)) {
			log.warn("REORDER DENIED | user={} | owner={} | orderId={}", username, order.getUsername(), orderId);
			throw new RuntimeException("You are not allowed to reorder this order");
		}

		for (OrderItem item : order.getItems()) {
			for (int i = 0; i < item.getQuantity(); i++) {
				cartService.addToCart(username, item.getProduct().getId());
			}

			log.debug("REORDER ITEM ADDED TO CART | product={} | qty={}", item.getProduct().getName(),
					item.getQuantity());
		}

		log.info("REORDER COMPLETED | user={} | orderId={}", username, orderId);
	}

	// ================= COUNT ORDERS =================
	public long countOrdersByUsername(String username) {
		return orderRepository.countByUsername(username);
	}
}
