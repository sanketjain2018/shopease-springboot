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

	// Stores only the IMAGE FILE NAME, not the image itself CHANGE FOR CLOUDINARY
	/*
	 * @Column(name = "image_name", nullable = false) private String imageUrl;
	 */
	@Column(name = "image_url", nullable = false, length = 500)
	private String imageUrl;

}
