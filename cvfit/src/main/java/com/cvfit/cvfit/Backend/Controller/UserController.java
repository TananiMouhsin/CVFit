package com.cvfit.cvfit.Backend.Controller;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvfit.cvfit.Backend.Entities.User;
import com.cvfit.cvfit.Backend.Services.UserService;
import com.cvfit.cvfit.Backend.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;  // Ajout du UserRepository
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/{userId}")
    public User getUserDetails(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping()
    public List<User> getAllUsersDetails() {
        return userService.getAllUsers();
    }

    @PostMapping
    public String addUserDetails(@RequestBody User user) {
        userService.addUser(user);
        return "Success";
    }

    @PutMapping
    public String updateUserDetails(@RequestBody User user) {
        userService.updateUser(user);
        return "Success";
    }

    @DeleteMapping("/{userId}")
    public String deleteUserDetails(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return "Success";
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser(HttpSession session) {
        System.out.println("🔍 Vérification de la session...");
        System.out.println("📌 Session ID reçue: " + session.getId());

        User user = (User) session.getAttribute("user");
        System.out.println("👤 Utilisateur dans la session: " + (user != null ? user.getUserEmail() : "null"));

        if (user == null) {
            return ResponseEntity.status(HttpStatus.OK).body("User not logged in");
        }

        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateAuthenticatedUser(@RequestBody User updatedUser, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        // Vérifier si l'email est déjà utilisé par un autre utilisateur
        if (updatedUser.getUserEmail() != null && !updatedUser.getUserEmail().isEmpty()) {
            if (!user.getUserEmail().equals(updatedUser.getUserEmail())) {
                Optional<User> existingUser = userRepository.findByUserEmail(updatedUser.getUserEmail());
                if (existingUser.isPresent()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already in use by another user");
                }
                user.setUserEmail(updatedUser.getUserEmail());
            }
        }

        // Mettre à jour le mot de passe uniquement s'il est fourni
        if (updatedUser.getUserPassword() != null && !updatedUser.getUserPassword().isEmpty()) {
            user.setUserPassword(passwordEncoder.encode(updatedUser.getUserPassword()));
        }

        // Mettre à jour le nom uniquement s'il est fourni
        if (updatedUser.getUserName() != null && !updatedUser.getUserName().isEmpty()) {
            user.setUserName(updatedUser.getUserName());
        }

        userRepository.save(user);

        // Mettre à jour la session
        session.setAttribute("user", user);

        return ResponseEntity.ok("User information updated successfully");
    }

}
