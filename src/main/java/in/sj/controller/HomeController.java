package in.sj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.sj.service.ProductService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private static final Logger log =
            LoggerFactory.getLogger(HomeController.class);

    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {

        log.info("HOME PAGE REQUESTED");

        long productCount = productService.getProductCount();
        log.debug("TOTAL PRODUCTS COUNT = {}", productCount);

        model.addAttribute("productCount", productCount);
        model.addAttribute("customerCount", 10542); // temp static
        model.addAttribute("uptime", "99.9%");
        model.addAttribute("support", "24/7");

        log.info("HOME PAGE DATA LOADED SUCCESSFULLY");

        return "home";
    }
}
