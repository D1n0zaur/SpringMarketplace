package com.marketplace.SelfPraktik.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String landingPage() {
        return "landing";
    }

    @GetMapping("/auth/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/auth/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/profile")
    public String profilePage() {
        return "profile";
    }

    @GetMapping("/home")
    public String mainPage() {
        return "home";
    }

    @GetMapping("/order/{id}")
    public String orderDetailsPage() {
        return "order";
    }

    @GetMapping("/cart")
    public String cartPage() {
        return "cart";
    }

    @GetMapping("/category/{id}")
    public String categoryPage() {
        return "category";
    }
}
