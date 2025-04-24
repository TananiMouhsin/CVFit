package com.cvfit.cvfit.Backend.Services;
import java.util.Optional;
import com.cvfit.cvfit.Backend.Entities.CV;
import com.cvfit.cvfit.Backend.Entities.User;
import com.cvfit.cvfit.Backend.repository.CVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class CVService {
    public CVService() {
        System.out.println("🛠️ CVService instancié !");
    }
    @Autowired
    private CVRepository cvRepository;

    public CV saveCV(MultipartFile file, User user, String strengths, String enhancements) throws IOException {
        String uploadDir = "uploads/";
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;
        Path filePath = Paths.get(uploadDir + uniqueFileName);

        Files.createDirectories(filePath.getParent());

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("📁 Fichier copié à : " + filePath.toAbsolutePath());

        // Vérifie si un CV existe déjà pour cet utilisateur
        Optional<CV> existingCvOpt = cvRepository.findTopByUserOrderByCvIdDesc(user);

        CV cv;
        if (existingCvOpt.isPresent()) {
            // 🔄 Mise à jour du CV existant
            cv = existingCvOpt.get();
            cv.setStrengths(strengths);
            cv.setEnhancements(enhancements);
            cv.setPdfCv(filePath.toString());
            cv.setCvName(originalFileName);
            System.out.println("🔄 CV existant mis à jour.");
        } else {
            // ➕ Création d’un nouveau CV
            cv = new CV();
            cv.setUser(user);
            cv.setStrengths(strengths);
            cv.setEnhancements(enhancements);
            cv.setPdfCv(filePath.toString());
            cv.setCvName(originalFileName);
            System.out.println("🆕 Nouveau CV créé.");
        }

        return cvRepository.save(cv);
    }



}
