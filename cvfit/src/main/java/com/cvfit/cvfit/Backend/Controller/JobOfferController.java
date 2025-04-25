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
                                               @RequestParam String cvName,
                                               HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non connecté.");
        }

        try {
            jobOfferService.saveJobOfferForUser(user, title, link, cvName);
            return ResponseEntity.ok("Offre de travail enregistrée avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur : " + e.getMessage());
        }
    }





    @DeleteMapping("/delete")
    public String deleteJobOffer(@RequestParam String cvTitle,
                                 @RequestParam String jobTitle,
                                 HttpSession session) {

        System.out.println("controller");
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "User not authenticated";
        }

        boolean deleted = jobOfferService.deleteJobOfferByTitleAndCv(user, cvTitle, jobTitle);
        return deleted ? "Job offer deleted successfully" : "Job offer not found";
    }




}