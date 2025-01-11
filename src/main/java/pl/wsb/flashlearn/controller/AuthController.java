package pl.wsb.flashlearn.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.wsb.flashlearn.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

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

    @GetMapping("/password_change")
    public String passwordChange() {return "password_change";}

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

    @PostMapping("/password_change")
    public String resetPassword(@RequestParam("old_password") String oldPassword,
                                @RequestParam("new_password") String newPassword,
                                Model model) {
        try {
            userService.resetPassword(oldPassword, newPassword);
            model.addAttribute("message", "Password change completed with success!");
            return "password_change";
        } catch (Exception e) {
            model.addAttribute("error", "Error during password change: " + e.getMessage());
            return "password_change";
        }
    }
}
