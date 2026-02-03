
package in.sj.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sj.entity.CartItem;

public interface CartRepository extends JpaRepository<CartItem, Long> {

	// Get all cart items for a user
	List<CartItem> findByUsername(String username);

	// Find specific product in user's cart
	Optional<CartItem> findByUsernameAndProductId(String username, Long productId);
}
