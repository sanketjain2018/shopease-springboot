package in.sj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private static final Logger log =
            LoggerFactory.getLogger(PageController.class);

    @GetMapping("/pages")
    public String home(Model model) {

        log.info("STATIC PAGE REQUESTED | page=home");

        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {

        log.info("STATIC PAGE REQUESTED | page=about");

        model.addAttribute("title", "About");
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {

        log.info("STATIC PAGE REQUESTED | page=contact");

        model.addAttribute("title", "Contact");
        return "contact";
    }
}
