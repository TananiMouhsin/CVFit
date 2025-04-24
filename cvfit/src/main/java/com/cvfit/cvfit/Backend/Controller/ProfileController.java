package com.cvfit.cvfit.Backend.Controller;

import com.cvfit.cvfit.Backend.DTOs.CVDTO;
import com.cvfit.cvfit.Backend.DTOs.CvDetailsDTO;
import com.cvfit.cvfit.Backend.DTOs.JobOfferDTO;
import com.cvfit.cvfit.Backend.Entities.CV;
import com.cvfit.cvfit.Backend.Entities.JobOffer;
import com.cvfit.cvfit.Backend.Entities.User;
import com.cvfit.cvfit.Backend.Services.ProfileService;
import com.cvfit.cvfit.Backend.Entities.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/cv/{cvId}/roles")
    public List<Role> getRolesByCvId(@PathVariable Long cvId) {

        return profileService.getRolesByCvId(cvId);
    }

    @GetMapping("/cv/{cvId}/details")
    public ResponseEntity<CvDetailsDTO> getCvDetails(@PathVariable Long cvId) {
        System.out.println(">>>> Endpoint called with cvId: " + cvId);
        CvDetailsDTO cvDetails = profileService.getCvDetails(cvId);
        return ResponseEntity.ok(cvDetails);
    }

    @GetMapping("/cv/{cvId}/jobs")
    public ResponseEntity<List<JobOfferDTO>> getJobOffersByCvId(@PathVariable Long cvId) {
        List<JobOfferDTO> offers = profileService.getJobOffersByCvId(cvId);
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/cvs/{userId}")
    public ResponseEntity<List<CVDTO>> getUserCVs(@PathVariable Long userId) {
        List<CVDTO> cvs = profileService.getUserCVs(userId);
        return ResponseEntity.ok(cvs);
    }

}
