package in.sj.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sj.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
}
