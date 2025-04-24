package com.cvfit.cvfit.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvfit.cvfit.Backend.Entities.JobOffer;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    List<JobOffer> findByCv_CvId(Long cvId);
}

