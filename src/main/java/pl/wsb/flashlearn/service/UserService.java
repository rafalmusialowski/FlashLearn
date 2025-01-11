package pl.wsb.flashlearn.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wsb.flashlearn.model.User;
import pl.wsb.flashlearn.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("A user with that name already exists");
        }

        var error = checkPasswordLength(password);
        if (error != null) {
            throw error;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public void resetPassword(String oldPassword, String newPassword) {
        var error = checkPasswordLength(newPassword);
        if (error != null) {
            throw error;
        }

        if (oldPassword.equals(newPassword)) {
            throw new IllegalArgumentException("New password must be different!");
        }

        User user = userRepository.findByUsername(getLoggedInUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not logged in"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect!");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }

    private static IllegalArgumentException checkPasswordLength(String newPassword) {
        if (newPassword.length() < 6) {
            return new IllegalArgumentException("Password must be at least 6 characters");
        }
        return null;
    }
}
