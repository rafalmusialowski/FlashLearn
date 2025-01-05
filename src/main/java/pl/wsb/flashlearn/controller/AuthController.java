package pl.wsb.flashlearn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Szablon Thymeleaf dla logowania
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Szablon Thymeleaf dla rejestracji
    }
}
