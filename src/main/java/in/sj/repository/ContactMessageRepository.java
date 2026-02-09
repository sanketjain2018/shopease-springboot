package in.sj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import in.sj.entity.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
	
}
