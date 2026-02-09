package in.sj.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.sj.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("""
			    SELECT p FROM Product p
			    WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			""")
	Page<Product> search(@Param("keyword") String keyword, Pageable pageable);
	
	// HOME PAGE CAROUSEL QUERIES
	
	 // Featured products (limit 12, latest first)
    List<Product> findTop12ByFeaturedTrueOrderByIdDesc();

    // New arrivals (latest by createdAt)
    List<Product> findTop12ByOrderByCreatedAtDesc();

    // Top selling products (highest soldCount first)
    List<Product> findTop12ByOrderBySoldCountDesc();
}
