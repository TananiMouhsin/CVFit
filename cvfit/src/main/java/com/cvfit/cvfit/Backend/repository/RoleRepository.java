package com.cvfit.cvfit.Backend.repository;

import com.cvfit.cvfit.Backend.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByCv_CvId(Long cvId);
    
}
