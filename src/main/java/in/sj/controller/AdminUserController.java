package in.sj.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.sj.entity.User;
import in.sj.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private static final Logger log =
            LoggerFactory.getLogger(AdminUserController.class);

    private final UserRepository userRepository;

    // ================= LIST USERS =================
    @GetMapping
    public String listUsers(Model model) {

        log.info("ADMIN VIEW USERS LIST");

        List<User> users = userRepository.findAll();
        log.debug("TOTAL USERS FETCHED | count={}", users.size());

        model.addAttribute("users", users);
        return "admin-users";
    }

    // ================= CHANGE ROLE =================
    @PostMapping("/change-role/{id}")
    public String changeRole(@PathVariable Long id, Principal principal) {

        String admin = principal != null ? principal.getName() : "UNKNOWN";
        log.info("CHANGE ROLE REQUEST | admin={} | userId={}", admin, id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("USER NOT FOUND FOR ROLE CHANGE | userId={}", id);
                    return new RuntimeException("User not found");
                });

        String oldRole = user.getRole();

        if ("ROLE_USER".equals(user.getRole())) {
            user.setRole("ROLE_ADMIN");
        } else {
            user.setRole("ROLE_USER");
        }

        userRepository.save(user);

        log.info("ROLE CHANGED | admin={} | username={} | {} -> {}",
                admin, user.getUsername(), oldRole, user.getRole());

        return "redirect:/admin/users";
    }

    // ================= ENABLE / DISABLE USER =================
    @PostMapping("/toggle/{id}")
    public String toggleUser(@PathVariable Long id, Principal principal) {

        String admin = principal.getName();
        log.info("TOGGLE USER REQUEST | admin={} | userId={}", admin, id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("USER NOT FOUND FOR TOGGLE | userId={}", id);
                    return new RuntimeException("User not found");
                });

        // Prevent admin disabling himself
        if (user.getUsername().equals(admin)) {
            log.warn("ADMIN SELF-DISABLE PREVENTED | admin={}", admin);
            return "redirect:/admin/users";
        }

        boolean oldStatus = user.isEnabled();
        user.setEnabled(!oldStatus);
        userRepository.save(user);

        log.info("USER STATUS CHANGED | admin={} | username={} | enabled {} -> {}",
                admin, user.getUsername(), oldStatus, user.isEnabled());

        return "redirect:/admin/users";
    }
}
