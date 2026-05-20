/*
 * package in.sj.entity;
 * 
 * import jakarta.persistence.*; import lombok.Data;
 * 
 * @Data
 * 
 * @Entity
 * 
 * @Table(name = "cart_items") public class CartItem {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
 * 
 * // which user owns this cart item
 * 
 * @Column(nullable = false) private String username;
 * 
 * @ManyToOne(fetch = FetchType.EAGER)
 * 
 * @JoinColumn(name = "product_id", nullable = false) private Product product;
 * 
 * @Column(nullable = false) private int quantity;
 * 
 * }
 */

package in.sj.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
    name = "cart_items",
    indexes = {
        @Index(name = "idx_cart_username",   columnList = "username"),
        @Index(name = "idx_cart_product_id", columnList = "product_id")
    }
)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;
}