package com.cvfit.cvfit.Backend.Services;

import com.cvfit.cvfit.Backend.Entities.CV;
import com.cvfit.cvfit.Backend.Entities.JobOffer;
import com.cvfit.cvfit.Backend.Entities.Role;
import com.cvfit.cvfit.Backend.Entities.User;
import com.cvfit.cvfit.Backend.repository.CVRepository;
import com.cvfit.cvfit.Backend.repository.JobOfferRepository;
import com.cvfit.cvfit.Backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleService {

    @Autowired
    private CVRepository cvRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void saveRoleForUser(User user, String title) {
        CV cv = cvRepository.findTopByUserOrderByCvIdDesc(user)
                .orElseThrow(() -> new RuntimeException("CV non trouvé pour l'utilisateur."));

        Role role = new Role(title, cv);
        roleRepository.save(role);
    }
}
