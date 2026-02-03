package in.sj.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log =
            LoggerFactory.getLogger(AdminController.class);

    // http://localhost:8080/admin/dashboard
    @GetMapping("/dashboard")
    public String dashboard(Principal principal) {

        if (principal != null) {
            log.info("ADMIN DASHBOARD ACCESS | admin={}", principal.getName());
        } else {
            log.warn("ADMIN DASHBOARD ACCESS | anonymous user");
        }

        return "admin-dashboard";
    }
}
