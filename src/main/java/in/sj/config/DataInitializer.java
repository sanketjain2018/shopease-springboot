package in.sj.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import in.sj.entity.User;
import in.sj.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initAdmin() {
        return args -> {

            String adminUsername = "admin";

            if (!userRepository.existsByUsername(adminUsername)) {

                User admin = new User();
                admin.setName("Administrator");
                admin.setUsername(adminUsername);
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");
                admin.setEnabled(true);

                userRepository.save(admin);

                System.out.println("✅ Default ADMIN user created: username=admin, password=admin123");
            } else {
                System.out.println("ℹ️ Admin user already exists, skipping creation");
            }
        };
    }
}