package com.cvfit.cvfit.Backend.repository;

import com.cvfit.cvfit.Backend.Entities.CV;
import com.cvfit.cvfit.Backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CVRepository extends JpaRepository<CV, Long> {

    // Juste la méthode de requête ici :
    Optional<CV> findTopByUserOrderByCvIdDesc(User user);
}
