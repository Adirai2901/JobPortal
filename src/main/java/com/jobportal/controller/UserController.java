package com.jobportal.controller;

import com.jobportal.dto.AuthResponse;
import com.jobportal.dto.LoginRequest;
import com.jobportal.dto.RegisterRequest;
import com.jobportal.model.User;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    // ✅ Register Endpoint
    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "User already exists with this email!";
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword()) // TODO: Hash this later
                .role(request.getRole())
                .build();

        userRepository.save(user);
        return "User registered successfully!";
    }

    // ✅ Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(request.getPassword())) {
                String token = jwtService.generateToken(user.getEmail());
                return ResponseEntity.ok(new AuthResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
