package in.sj.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class OrderSuccessController {

    private static final Logger log =
            LoggerFactory.getLogger(OrderSuccessController.class);

    @GetMapping("/order-success")
    public String success(Principal principal) {

        if (principal != null) {
            log.info("ORDER SUCCESS PAGE VIEWED | user={}", principal.getName());
        } else {
            log.warn("ORDER SUCCESS PAGE VIEWED | anonymous user");
        }

        return "order-success";
    }
}
