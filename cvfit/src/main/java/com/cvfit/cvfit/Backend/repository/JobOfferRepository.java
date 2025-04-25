package com.cvfit.cvfit.Backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvfit.cvfit.Backend.Entities.CV;
import com.cvfit.cvfit.Backend.Entities.JobOffer;
import com.cvfit.cvfit.Backend.Entities.User;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    List<JobOffer> findByCv_CvId(Long cvId);

    Optional<JobOffer> findByTitleIgnoreCaseAndCv(String title, CV cv);


}

