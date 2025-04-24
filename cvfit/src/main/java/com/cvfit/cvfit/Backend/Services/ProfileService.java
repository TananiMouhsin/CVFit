package com.cvfit.cvfit.Backend.Services;

import com.cvfit.cvfit.Backend.DTOs.CVDTO;
import com.cvfit.cvfit.Backend.DTOs.CvDetailsDTO;
import com.cvfit.cvfit.Backend.DTOs.JobOfferDTO;
import com.cvfit.cvfit.Backend.Entities.CV;
import com.cvfit.cvfit.Backend.Entities.JobOffer;
import com.cvfit.cvfit.Backend.Entities.User;

import com.cvfit.cvfit.Backend.Entities.Role;
import com.cvfit.cvfit.Backend.repository.CVRepository;
import com.cvfit.cvfit.Backend.repository.JobOfferRepository;
import com.cvfit.cvfit.Backend.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private RoleRepository roleRepository;
    private CVRepository cvRepository;
    private JobOfferRepository jobOfferRepository;

    @Autowired
    public ProfileService(JobOfferRepository jobOfferRepository, CVRepository cvRepository) {
        this.jobOfferRepository = jobOfferRepository;
        this.cvRepository = cvRepository;
    }

    public List<Role> getRolesByCvId(Long cvId) {
        System.out.println(cvId);
        return roleRepository.findByCv_CvId(cvId);
    }

    public CvDetailsDTO getCvDetails(Long cvId) {
        Optional<CV> optionalCv = cvRepository.findById(cvId);
        if (optionalCv.isPresent()) {
            CV cv = optionalCv.get();
            return new CvDetailsDTO(cv.getStrengths(), cv.getEnhancements());
        } else {
            throw new NoSuchElementException("CV not found with id: " + cvId);
        }
    }

    public List<JobOffer> getJobsByCvId(Long cvId) {
        return jobOfferRepository.findByCv_CvId(cvId);
    }

    public List<JobOfferDTO> getAllJobOffers() {
        return jobOfferRepository.findAll()
                .stream()
                .map(offer -> new JobOfferDTO(offer.getTitle(), offer.getLink()))
                .collect(Collectors.toList());
    }

    public List<JobOfferDTO> getJobOffersByCvId(Long cvId) {
        List<JobOffer> offers = jobOfferRepository.findByCv_CvId(cvId);
        return offers.stream()
                .map(offer -> new JobOfferDTO(offer.getTitle(), offer.getLink()))
                .collect(Collectors.toList());
    }

    public List<CVDTO> getUserCVs(Long userId) {
        return cvRepository.findByUser_UserId(userId)
                .stream()
                .map(cv -> new CVDTO(cv.getPdfCv()))
                .collect(Collectors.toList());
    }

}
