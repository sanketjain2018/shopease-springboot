package in.sj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.sj.service.ContactService;
import in.sj.service.ProductService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PageController {

    private static final Logger log =
            LoggerFactory.getLogger(PageController.class);

    private final ProductService productService;
    private final ContactService contactService;

    @GetMapping("/about")
    public String about(Model model) {

        log.info("STATIC PAGE REQUESTED | page=about");

        // Dynamic data for About page
        model.addAttribute("title", "About");
        model.addAttribute("productCount", productService.getProductCount());
        model.addAttribute("userCount", 120);     // later from DB
        model.addAttribute("orderCount", 350);    // later from DB
        model.addAttribute("appVersion", "1.0.0");
        model.addAttribute("buildYear", 2026);

        return "about";
    }
    

    @GetMapping("/contact")
    public String contact(Model model) {

        log.info("STATIC PAGE REQUESTED | page=contact");

        model.addAttribute("title", "Contact");
        return "contact";
    }
    
    @PostMapping("/contact/send")
    public String sendContactMessage(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String message
    ) {

        contactService.saveMessage(name, email, subject, message);

        return "redirect:/contact?success";
    }
}
