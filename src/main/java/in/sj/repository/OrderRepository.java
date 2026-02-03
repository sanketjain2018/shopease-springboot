
package in.sj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sj.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	// Get all orders of logged-in user
	List<Order> findByUsername(String username);
}
