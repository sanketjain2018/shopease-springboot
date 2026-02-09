package in.sj.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	private String category;

	@Column(nullable = false)
	private Double price;

	private Integer stock;

	@Column(name = "image_url", nullable = true, length = 500)
	private String imageUrl;
	
	// for home pages featured  
	
	@Column(nullable = false)
	private Boolean featured = false;

	// For "New Arrivals"
	@Column(nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	// For "Top Selling"
	@Column(nullable = false)
	private Long soldCount = 0L;

}
