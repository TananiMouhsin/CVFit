package com.cvfit.cvfit.Backend.repository;

import com.cvfit.cvfit.Backend.Entities.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
}
