package com.jobportal.controller;

import com.jobportal.model.User;
import com.jobportal.model.Role;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // shows register.html
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam String name,
                                      @RequestParam String email,
                                      @RequestParam String password,
                                      @RequestParam Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            // Optional: handle duplicate email case
            return "redirect:/register?error=email_taken";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepository.save(user);
        return "redirect:/login?success"; // redirect to login after registering
    }
}
