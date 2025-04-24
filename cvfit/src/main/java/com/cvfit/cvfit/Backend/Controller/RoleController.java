package com.cvfit.cvfit.Backend.Controller;

import com.cvfit.cvfit.Backend.Entities.User;
import com.cvfit.cvfit.Backend.Services.RoleService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/save")
    public ResponseEntity<String> saveRole(@RequestParam String title,
                                           HttpSession session) {
        User user = (User) session.getAttribute("user");
  
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non connecté.");
        }

        try {
            roleService.saveRoleForUser(user, title);
            return ResponseEntity.ok("Rôle enregistré avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur : " + e.getMessage());
        }
    }
}
