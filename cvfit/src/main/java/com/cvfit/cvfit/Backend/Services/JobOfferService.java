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

    public void saveJobOfferForUser(User user, String title, String link) {
        // Vérifier si le CV existe déjà pour l'utilisateur (ici on suppose que vous avez une méthode pour retrouver un CV)
        Optional<CV> optionalCV = cvRepository.findTopByUserOrderByCvIdDesc(user);  // Exemple avec le dernier CV de l'utilisateur

        CV cv;

        if (optionalCV.isPresent()) {
            // Si le CV existe, récupère l'ID du CV existant
            cv = optionalCV.get();
            System.out.println("✅ CV existant trouvé pour l'utilisateur.");
        } else {
            // Si le CV n'existe pas, créez un nouveau CV avec des valeurs vides pour strengths et enhancements
            cv = new CV();
            cv.setUser(user);  // Associer le CV à l'utilisateur
            cv.setCvName("defaultCvName");  // Utilisez un nom par défaut ou un autre critère pour le nom du CV
            cv.setStrengths("");  // Forces vides
            cv.setEnhancements("");  // Améliorations vides
            cv.setPdfCv("uploads/defaultCv.pdf");  // Chemin par défaut du fichier PDF
            cv = cvRepository.save(cv);  // Sauvegarder le CV dans la base de données
            System.out.println("➕ Nouveau CV créé avec cvName: " + cv.getCvName());
        }

        // Créer et sauvegarder l'offre de travail associée au CV récupéré ou créé
        JobOffer jobOffer = new JobOffer(title, link, cv);  // Associer l'offre de travail au CV
        jobOfferRepository.save(jobOffer);  // Sauvegarder l'offre dans la base de données
        System.out.println("💼 Offre enregistrée avec succès avec l'ID du CV: " + cv.getCvId());
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
