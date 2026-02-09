package in.sj.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.sj.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // ================= USER ORDERS =================

    // Get all orders of logged-in user
    List<Order> findByUsername(String username);

    long countByUsername(String username);

    // Total revenue of all orders (admin)
    @Query("select coalesce(sum(o.totalAmount), 0) from Order o")
    Double getTotalRevenue();

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.items WHERE o.id = :id")
    Optional<Order> findByIdWithItems(@Param("id") Long id);

    // Total money spent by user
    @Query("select coalesce(sum(o.totalAmount), 0) from Order o where o.username = :username")
    Double getTotalSpentByUsername(@Param("username") String username);

    // Last (most recent) order of user
    Optional<Order> findTopByUsernameOrderByOrderDateDesc(String username);

    // Recent orders (latest N) of user
    @Query("select o from Order o where o.username = :username order by o.orderDate desc")
    List<Order> findRecentOrders(@Param("username") String username, Pageable pageable);

    // ================= ADMIN ANALYTICS =================

    // Monthly sales for chart: returns [monthNumber, totalAmount]
    @Query("""
           SELECT MONTH(o.orderDate), COALESCE(SUM(o.totalAmount), 0)
           FROM Order o
           GROUP BY MONTH(o.orderDate)
           ORDER BY MONTH(o.orderDate)
           """)
    List<Object[]> findMonthlySales();
}
