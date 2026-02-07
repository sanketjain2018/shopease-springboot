package in.sj.entity;

import jakarta.persistence.*;
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

}
