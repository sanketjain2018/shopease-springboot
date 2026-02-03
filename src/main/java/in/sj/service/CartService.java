package in.sj.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import in.sj.entity.CartItem;
import in.sj.entity.Product;
import in.sj.repository.CartRepository;
import in.sj.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private static final Logger log =
            LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    // ================= ADD TO CART =================
    public void addToCart(String username, Long productId) {

        log.info("ADD TO CART | user={} | productId={}", username, productId);

        CartItem item = cartRepository
                .findByUsernameAndProductId(username, productId)
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + 1);
            log.debug("CART ITEM UPDATED | productId={} | newQty={}",
                    productId, item.getQuantity());
        } else {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> {
                        log.warn("PRODUCT NOT FOUND | productId={}", productId);
                        return new RuntimeException("Product not found");
                    });

            item = new CartItem();
            item.setUsername(username);
            item.setProduct(product);
            item.setQuantity(1);

            log.debug("NEW CART ITEM CREATED | product={} | qty=1",
                    product.getName());
        }

        cartRepository.save(item);
        log.info("ADD TO CART COMPLETED | user={} | productId={}", username, productId);
    }

    // ================= GET CART ITEMS =================
    public List<CartItem> getCartItems(String username) {

        log.debug("FETCH CART ITEMS | user={}", username);

        List<CartItem> items = cartRepository.findByUsername(username);

        log.debug("CART ITEMS COUNT | user={} | count={}",
                username, items.size());

        return items;
    }

    // ================= CHANGE QUANTITY =================
    public void changeQuantity(String username, Long productId, int change) {

        log.info("CHANGE CART QUANTITY | user={} | productId={} | change={}",
                username, productId, change);

        CartItem item = cartRepository
                .findByUsernameAndProductId(username, productId)
                .orElseThrow(() -> {
                    log.warn("CART ITEM NOT FOUND | user={} | productId={}",
                            username, productId);
                    return new RuntimeException("Cart item not found");
                });

        int newQty = item.getQuantity() + change;

        if (newQty <= 0) {
            cartRepository.delete(item);
            log.info("CART ITEM REMOVED | user={} | productId={}",
                    username, productId);
        } else {
            item.setQuantity(newQty);
            cartRepository.save(item);

            log.debug("CART QUANTITY UPDATED | user={} | productId={} | qty={}",
                    username, productId, newQty);
        }
    }

    // ================= REMOVE ITEM =================
    public void removeItem(String username, Long productId) {

        log.info("REMOVE CART ITEM | user={} | productId={}",
                username, productId);

        cartRepository.findByUsernameAndProductId(username, productId)
                .ifPresent(item -> {
                    cartRepository.delete(item);
                    log.info("CART ITEM REMOVED | user={} | productId={}",
                            username, productId);
                });
    }

    // ================= CALCULATE TOTAL =================
    public double calculateTotal(String username) {

        log.debug("CALCULATE CART TOTAL | user={}", username);

        double total = getCartItems(username).stream()
                .mapToDouble(i ->
                        i.getProduct().getPrice() * i.getQuantity())
                .sum();

        log.debug("CART TOTAL | user={} | total={}", username, total);

        return total;
    }

    // ================= CLEAR CART =================
    public void clearCart(String username) {

        log.info("CLEAR CART | user={}", username);

        List<CartItem> items = getCartItems(username);
        cartRepository.deleteAll(items);

        log.info("CART CLEARED | user={} | itemsRemoved={}",
                username, items.size());
    }
}
