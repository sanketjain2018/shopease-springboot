package in.sj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import in.sj.entity.User;
import in.sj.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log =
            LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    // ================= LOGIN PAGE =================
    @GetMapping("/login")
    public String login() {
        log.info("LOGIN PAGE REQUESTED");
        return "login";
    }

    // ================= REGISTER PAGE =================
    @GetMapping("/register")
    public String registerForm(Model model) {

        log.info("REGISTER PAGE REQUESTED");

        model.addAttribute("user", new User());
        return "register";
    }

    // ================= REGISTER USER =================
    @PostMapping("/register")
    public String register(@ModelAttribute User user) {

        log.info("REGISTER REQUEST RECEIVED | username={} | email={}",
                user.getUsername(), user.getEmail());

        try {
            userService.register(user);
            log.info("USER REGISTERED SUCCESSFULLY | username={}", user.getUsername());
        } catch (Exception e) {
            log.error("USER REGISTRATION FAILED | username={} | reason={}",
                    user.getUsername(), e.getMessage());
            throw e;
        }

        return "redirect:/login";
    }
}
