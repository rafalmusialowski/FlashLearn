package pl.wsb.flashlearn.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.wsb.flashlearn.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import pl.wsb.flashlearn.model.User;

import java.util.List;


@Controller
public class AuthController {

    @GetMapping("/**")
    public String home() {
        return "redirect:/login"; // Przekierowanie na stronę logowania
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Szablon Thymeleaf dla logowania
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Szablon Thymeleaf dla rejestracji
    }

    @GetMapping("/all-users")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        try {
            // Wywołanie logiki rejestracji użytkownika w UserService
            userService.registerUser(username, password);
            // Dodanie monitu o sukcesie do modelu
            model.addAttribute("message", "Rejestracja zakończona sukcesem!");
            return "register";
        } catch (Exception e) {
            // Obsługa błędu i dodanie monitu o błędzie
            model.addAttribute("error", "Błąd podczas rejestracji: " + e.getMessage());
            return "register"; // Powrót do strony rejestracji
        }
    }

}
