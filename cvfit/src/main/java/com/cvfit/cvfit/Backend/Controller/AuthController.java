package com.cvfit.cvfit.Backend.Controller;

import java. util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvfit.cvfit.Backend.DTOs.AuthRequest;
import com.cvfit.cvfit.Backend.Entities.User;
import com.cvfit.cvfit.Backend.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUserEmail(user.getUserEmail());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists with this email");
        }

        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request, HttpServletRequest req) {
        User user = userRepository.findByUserEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getUserPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Store user in session
        HttpSession session = req.getSession(true); // 🔥 Assure que la session est créée
        session.setAttribute("user", user);

        System.out.println("✅ Utilisateur stocké en session: " + user.getUserEmail());
        System.out.println("📌 Session ID après login: " + session.getId());

        return ResponseEntity.ok("Login successful");
    }


    @PostMapping("/s")
    public String ss(){
        return "llll";
    }



    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        if (session != null) {
            System.out.println("📌 Session avant invalidation: " + session.getId());
            session.invalidate();
            System.out.println("✅ Session invalidée !");
        } else {
            System.out.println("⚠️ Aucune session trouvée !");
        }

        return ResponseEntity.ok("Logged out successfully");
    }


}
