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

    /**
     * Enregistre une offre de travail pour le dernier CV de l'utilisateur connecté.
     */
    public void saveJobOfferForUser(User user, String title, String link) throws Exception {
        Optional<CV> optionalCV = cvRepository.findTopByUserOrderByCvIdDesc(user);
        if (optionalCV.isEmpty()) {
            throw new Exception("Aucun CV trouvé pour cet utilisateur.");
        }

        CV latestCV = optionalCV.get();

        JobOffer jobOffer = new JobOffer(title, link, latestCV);
        jobOfferRepository.save(jobOffer);
    }

    public boolean deleteJobOfferById(User user, Long offerId) {
        Optional<CV> cvOpt = cvRepository.findTopByUserOrderByCvIdDesc(user);

        if (cvOpt.isEmpty()) {
            throw new RuntimeException("CV non trouvé pour l'utilisateur.");
        }

        CV userCv = cvOpt.get();

        Optional<JobOffer> offerOpt = jobOfferRepository.findById(offerId);
        if (offerOpt.isPresent()) {
            JobOffer offer = offerOpt.get();
            // Make sure the offer belongs to the user's CV
            if (offer.getCv().getCvId().equals(userCv.getCvId())) {
                jobOfferRepository.delete(offer);
                return true;
            }
        }

        return false;
    }

}
