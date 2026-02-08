package in.sj.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.sj.entity.PasswordResetToken;
import in.sj.entity.User;
import in.sj.repository.PasswordResetTokenRepository;
import in.sj.repository.UserRepository;
import in.sj.service.MailSenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final MailSenderService mailSenderService;   //  use interface (dev/prod)
    private final PasswordEncoder passwordEncoder;

    // 1) Show "Forgot Password" page
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    // 2) Handle email submit
    @Transactional
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email,
                                        HttpServletRequest request,
                                        Model model) {

        var userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "No user found with this email");
            return "forgot-password";
        }

        User user = userOpt.get();

        // Delete old token for this user (if any)
        tokenRepository.deleteByUserId(user.getId());

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30)); // valid for 30 minutes

        tokenRepository.save(resetToken);

        // Build base URL dynamically (works for Local + Render)
        String baseUrl = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            baseUrl += ":" + request.getServerPort();
        }

        String resetLink = baseUrl + "/reset-password?token=" + token;

        //  Send email via profile-based service
        // dev  -> Gmail SMTP
        // prod -> Brevo API
        mailSenderService.sendResetPasswordMail(user.getEmail(), resetLink);

        model.addAttribute("message", "Password reset link sent to your email");
        return "forgot-password";
    }

    // 3) Show "Reset Password" page
    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, Model model) {

        var tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty() || tokenOpt.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Invalid or expired token");
            return "reset-password";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }

    // 4) Handle new password submit
    @Transactional
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String token,
                                       @RequestParam String password,
                                       Model model) {

        var tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty() || tokenOpt.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Invalid or expired token");
            return "reset-password";
        }

        User user = tokenOpt.get().getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        // Invalidate token after use
        tokenRepository.delete(tokenOpt.get());

        return "redirect:/login?resetSuccess";
    }
}
