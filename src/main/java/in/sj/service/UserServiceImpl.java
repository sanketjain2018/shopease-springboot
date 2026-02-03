package in.sj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.sj.entity.User;
import in.sj.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log =
            LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ================= REGISTER =================
    @Override
    public void register(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);
    }

    // ================= FIND =================
    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ================= GENERIC UPDATE =================
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    // ================= PROFILE GET =================
    @Override
    public User getUserForProfile(String username) {
        return findByUsername(username);
    }

    // ================= PROFILE UPDATE =================
    @Override
    public void updateProfile(String username, User updatedUser) {

        User existingUser = findByUsername(username);

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());

        userRepository.save(existingUser);
    }
}
