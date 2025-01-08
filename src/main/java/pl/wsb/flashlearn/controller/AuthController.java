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
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
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
            userService.registerUser(username, password);
            model.addAttribute("message", "Registration completed with success!");
            return "register";
        } catch (Exception e) {
            model.addAttribute("error", "Error during registration: " + e.getMessage());
            return "register";
        }
    }

}
