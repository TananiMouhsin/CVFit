package com.cvfit.cvfit.Backend.Controller;

import com.cvfit.cvfit.Backend.Entities.CV;
import com.cvfit.cvfit.Backend.Entities.User;
import com.cvfit.cvfit.Backend.Services.CVService;
import com.cvfit.cvfit.Backend.repository.CVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@RestController
@RequestMapping("/cv")
public class CVController {

    @Autowired
    private CVService cvService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCV(@RequestParam("file") MultipartFile file,
                                           @RequestParam("strengths") String strengths,
                                           @RequestParam("enhancements") String enhancements,
                                           HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non connecté.");
        }
        System.out.println("📥 Début de l'upload...");
        try {
            System.out.println("📥 Début de l'upload...");
            cvService.saveCV(file, user, strengths, enhancements);
            return ResponseEntity.ok("CV uploadé avec succès.");
        } catch (Exception e) {
            System.out.println("❌ Exception capturée dans le contrôleur : " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur : " + e.getMessage());
        }
    }

}
