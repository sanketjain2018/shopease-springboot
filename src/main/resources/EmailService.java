/*
 * package in.sj.controller;
 * 
 * import java.time.LocalDateTime; import java.util.UUID;
 * 
 * import org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.*;
 * 
 * import in.sj.entity.PasswordResetToken; import in.sj.entity.User; import
 * in.sj.repository.PasswordResetTokenRepository; import
 * in.sj.repository.UserRepository; import in.sj.service.EmailService; import
 * lombok.RequiredArgsConstructor;
 * 
 * @Controller
 * 
 * @RequiredArgsConstructor public class ForgotPasswordController {
 * 
 * private final UserRepository userRepository; private final
 * PasswordResetTokenRepository tokenRepository; private final EmailService
 * emailService; private final PasswordEncoder passwordEncoder;
 * 
 * // 1) Show "Forgot Password" page
 * 
 * @GetMapping("/forgot-password") public String forgotPasswordPage() { return
 * "forgot-password"; }
 * 
 * // 2) Handle email submit
 * 
 * @PostMapping("/forgot-password") public String
 * processForgotPassword(@RequestParam String email, Model model) {
 * 
 * var userOpt = userRepository.findByEmail(email); if (userOpt.isEmpty()) {
 * model.addAttribute("error", "No user found with this email"); return
 * "forgot-password"; }
 * 
 * User user = userOpt.get();
 * 
 * // delete old token for this user (if any)
 * tokenRepository.deleteByUserId(user.getId());
 * 
 * String token = UUID.randomUUID().toString();
 * 
 * PasswordResetToken resetToken = new PasswordResetToken();
 * resetToken.setToken(token); resetToken.setUser(user);
 * resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30)); // valid for
 * 30 minutes
 * 
 * tokenRepository.save(resetToken);
 * 
 * // TODO: change domain to your real domain in prod String resetLink =
 * "http://localhost:8080/reset-password?token=" + token;
 * 
 * emailService.sendResetLink(user.getEmail(), resetLink);
 * 
 * model.addAttribute("message", "Password reset link sent to your email");
 * return "forgot-password"; }
 * 
 * // 3) Show "Reset Password" page
 * 
 * @GetMapping("/reset-password") public String resetPasswordPage(@RequestParam
 * String token, Model model) {
 * 
 * var tokenOpt = tokenRepository.findByToken(token); if (tokenOpt.isEmpty() ||
 * tokenOpt.get().getExpiryDate().isBefore(LocalDateTime.now())) {
 * model.addAttribute("error", "Invalid or expired token"); return
 * "reset-password"; }
 * 
 * model.addAttribute("token", token); return "reset-password"; }
 * 
 * // 4) Handle new password submit
 * 
 * @PostMapping("/reset-password") public String
 * processResetPassword(@RequestParam String token,
 * 
 * @RequestParam String password, Model model) {
 * 
 * var tokenOpt = tokenRepository.findByToken(token); if (tokenOpt.isEmpty() ||
 * tokenOpt.get().getExpiryDate().isBefore(LocalDateTime.now())) {
 * model.addAttribute("error", "Invalid or expired token"); return
 * "reset-password"; }
 * 
 * User user = tokenOpt.get().getUser();
 * user.setPassword(passwordEncoder.encode(password));
 * userRepository.save(user);
 * 
 * tokenRepository.delete(tokenOpt.get()); // invalidate token
 * 
 * return "redirect:/login?resetSuccess"; } }
 */