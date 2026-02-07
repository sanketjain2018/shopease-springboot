
package in.sj.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.sj.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	// Get all orders of logged-in user
	List<Order> findByUsername(String username);

	long countByUsername(String username);

	@Query("select coalesce(sum(o.totalAmount), 0) from Order o")
	Double getTotalRevenue();
	@Query("SELECT o FROM Order o LEFT JOIN FETCH o.items WHERE o.id = :id")
	Optional<Order> findByIdWithItems(@Param("id") Long id);

}
