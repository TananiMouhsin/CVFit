package com.cvfit.cvfit.Backend.Controller;

import com.cvfit.cvfit.Backend.Entities.CV;
import com.cvfit.cvfit.Backend.Entities.JobOffer;
import com.cvfit.cvfit.Backend.Entities.User;
import com.cvfit.cvfit.Backend.Services.JobOfferService;
import com.cvfit.cvfit.Backend.repository.CVRepository;
import com.cvfit.cvfit.Backend.repository.JobOfferRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-offers")
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    @PostMapping("/save")
    public ResponseEntity<String> saveJobOffer(@RequestParam String title,
                                               @RequestParam String link,
                                               HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non connecté.");
        }

        try {
            jobOfferService.saveJobOfferForUser(user, title, link);
            return ResponseEntity.ok("Offre de travail enregistrée avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur : " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobOffer(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non connecté.");
        }

        try {
            boolean deleted = jobOfferService.deleteJobOfferById(user, id);
            if (deleted) {
                return ResponseEntity.ok("Offre supprimée avec succès !");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cette offre ne vous appartient pas ou n'existe pas.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression : " + e.getMessage());
        }
    }

}
