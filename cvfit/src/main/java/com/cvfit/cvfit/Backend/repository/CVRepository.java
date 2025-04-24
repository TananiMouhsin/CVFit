package com.cvfit.cvfit.Backend.repository;

import com.cvfit.cvfit.Backend.Entities.CV;

import com.cvfit.cvfit.Backend.Entities.User;


import java.util.Optional;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CVRepository extends JpaRepository<CV, Long> {
    
    List<CV> findByUser_UserId(Long userId);

    // Recherche par utilisateur et nom du CV
    Optional<CV> findByUserAndCvName(User user, String cvName);

    // Recherche du dernier CV pour l'utilisateur

    Optional<CV> findTopByUserOrderByCvIdDesc(User user);


}
