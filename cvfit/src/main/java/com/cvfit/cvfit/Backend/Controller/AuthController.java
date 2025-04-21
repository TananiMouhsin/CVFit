package com.cvfit.cvfit.Backend.Controller;

import com.cvfit.cvfit.Backend.DTOs.AuthRequest;
import com.cvfit.cvfit.Backend.Entities.User;
import com.cvfit.cvfit.Backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

        HttpSession session = req.getSession(true);
        session.setAttribute("user", user);

        // Créer un auth token vide (ou avec rôles si tu les gères)
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getUserEmail(), null, List.of());

        // Créer un SecurityContext
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authToken);
        SecurityContextHolder.setContext(securityContext);

        // ✅ Lier le SecurityContext à la session
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        System.out.println("✅ Utilisateur authentifié : " + user.getUserEmail());
        return ResponseEntity.ok("Login successful");
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
