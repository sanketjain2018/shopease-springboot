package in.sj.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import in.sj.service.AdminDashboardService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private static final Logger log =
            LoggerFactory.getLogger(AdminController.class);

    private final AdminDashboardService dashboardService;

    // http://localhost:8080/admin/dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        if (principal != null) {
            log.info("ADMIN DASHBOARD ACCESS | admin={}", principal.getName());
        } else {
            log.warn("ADMIN DASHBOARD ACCESS | anonymous user");
        }

        //  Add dashboard data
        model.addAttribute("totalProducts", dashboardService.getTotalProducts());
        model.addAttribute("totalUsers", dashboardService.getTotalUsers());
        model.addAttribute("totalOrders", dashboardService.getTotalOrders());
        model.addAttribute("totalRevenue", dashboardService.getTotalRevenue());

        return "admin-dashboard"; // your existing page
    }
}
