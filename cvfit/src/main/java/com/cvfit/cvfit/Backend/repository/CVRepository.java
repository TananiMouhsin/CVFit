package com.cvfit.cvfit.Backend.repository;

import com.cvfit.cvfit.Backend.Entities.CV;
import com.cvfit.cvfit.Backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CVRepository extends JpaRepository<CV, Long> {

    // Recherche par utilisateur et nom du CV
    Optional<CV> findByUserAndCvName(User user, String cvName);

    // Recherche du dernier CV pour l'utilisateur
    Optional<CV> findTopByUserOrderByCvIdDesc(User user);
}
