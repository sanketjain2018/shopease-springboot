package in.sj.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "contact_messages")
public class ContactMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 150)
	private String email;

	@Column(nullable = false, length = 200)
	private String subject;

	@Column(nullable = false, length = 2000)
	private String message;

	@Column(nullable = false)
	private LocalDateTime createdAt;
}
