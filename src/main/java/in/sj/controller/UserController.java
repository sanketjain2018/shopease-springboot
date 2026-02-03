package in.sj.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.sj.entity.User;
import in.sj.service.CartService;
import in.sj.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log =
            LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
  //  private final CartService cartService;

    public UserController(UserService userService, CartService cartService) {
        this.userService = userService;
       // this.cartService = cartService;
    }

 // ================= USER DASHBOARD =================
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        model.addAttribute("username", principal.getName());
        return "user-dashboard";
    }


	

    // ================= UPDATE PROFILE =================
    @PostMapping("/profile/update")
    public String updateProfile(User user, Principal principal) {

        String username = principal.getName();
        log.info("USER PROFILE UPDATE REQUEST | loggedInUser={} | targetUser={}",
                username, user.getUsername());

        userService.updateUser(user);

        log.info("USER PROFILE UPDATED SUCCESSFULLY | user={}", user.getUsername());

        return "redirect:/user/dashboard";
    }
}
