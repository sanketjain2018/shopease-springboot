package in.sj.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import in.sj.entity.User;
import in.sj.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    // ================= SHOW PROFILE =================
    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {

        User user = userService.getUserForProfile(principal.getName());
        model.addAttribute("user", user);

        return "profile";
    }

    // ================= UPDATE PROFILE =================
    @PostMapping("/profile")
    public String updateProfile(User formUser,
                                @RequestParam(value = "image", required = false) MultipartFile image,
                                Principal principal) {

        userService.updateProfile(principal.getName(), formUser, image);

        return "redirect:/user/profile?success";
    }
}
