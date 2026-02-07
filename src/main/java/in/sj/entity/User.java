package in.sj.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ================= BASIC INFO =================

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // ================= SECURITY =================

    @Column(nullable = false)
    private String role; // ROLE_USER / ROLE_ADMIN

    @Column(nullable = false)
    private boolean enabled = true;

    // ================= PROFILE =================

    // Profile image stored on Cloudinary (URL only)
    
    @Column(name = "profile_image_url",  nullable = true , length = 500)
    private String profileImageUrl;

    // Address (optional fields)
    @Column(name = "address_line1", nullable = true)
    private String addressLine1;

    @Column(nullable = true)
    private String city;

    @Column(nullable = true)
    private String state;

    @Column(nullable = true)
    private String pincode;
}
