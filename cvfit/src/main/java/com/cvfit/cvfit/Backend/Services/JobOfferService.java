package com.cvfit.cvfit.Backend.Services;

import com.cvfit.cvfit.Backend.Entities.CV;
import com.cvfit.cvfit.Backend.Entities.JobOffer;
import com.cvfit.cvfit.Backend.Entities.User;
import com.cvfit.cvfit.Backend.repository.CVRepository;
import com.cvfit.cvfit.Backend.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Autowired
    private CVRepository cvRepository;

    public void saveJobOfferForUser(User user, String title, String link, String cvName) {
        // Vérifier si le CV existe déjà pour l'utilisateur et le nom donné
        Optional<CV> optionalCV = cvRepository.findByUserAndCvName(user, cvName);
        CV cv;

        if (optionalCV.isPresent()) {
            // Le CV existe déjà → on récupère son ID
            cv = optionalCV.get();
            System.out.println("✅ CV existant trouvé : ID = " + cv.getCvId());
        } else {
            // Sinon, on crée un nouveau CV
            cv = new CV();
            cv.setUser(user);                      // Associer le CV à l'utilisateur
            cv.setCvName(cvName);                  // Nom donné
            cv.setStrengths("");                   // Champ strengths vide
            cv.setEnhancements("");                // Champ enhancements vide
            cv.setPdfCv("uploads/" + cvName);      // Chemin PDF par défaut
            cv = cvRepository.save(cv);            // Enregistrer le nouveau CV
            System.out.println("➕ Nouveau CV créé avec ID = " + cv.getCvId());
        }

        // Créer et enregistrer l'offre de travail liée au CV
        JobOffer jobOffer = new JobOffer(title, link, cv);
        jobOfferRepository.save(jobOffer);
        System.out.println("💼 Offre enregistrée avec succès (CV ID = " + cv.getCvId() + ")");
    }



    public boolean deleteJobOfferByIdAndTitle(User user, Long offerId, String title) {
        Optional<CV> cvOpt = cvRepository.findTopByUserOrderByCvIdDesc(user);

        if (cvOpt.isEmpty()) {
            throw new RuntimeException("CV non trouvé pour l'utilisateur.");
        }

        CV userCv = cvOpt.get();

        Optional<JobOffer> offerOpt = jobOfferRepository.findById(offerId);
        if (offerOpt.isPresent()) {
            JobOffer offer = offerOpt.get();

            if (offer.getCv().getCvId().equals(userCv.getCvId())
                    && offer.getTitle().equalsIgnoreCase(title)) {
                jobOfferRepository.delete(offer);
                return true;
            }
        }

        return false;
    }
}
