package com.cvfit.cvfit.Backend.Services;

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
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        System.out.println("UPLOAD DIR: " + filePath.toAbsolutePath());

        Files.createDirectories(filePath.getParent()); // Crée le dossier s'il n'existe pas

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("FICHIER COPIÉ !");
        } catch (IOException e) {
            System.err.println("ÉCHEC COPIE FICHIER : " + e.getMessage());
            throw e;
        }

        CV cv = new CV();
        cv.setUser(user);
        cv.setPdfCv(filePath.toString());
        cv.setStrengths(strengths);
        cv.setEnhancements(enhancements);

        return cvRepository.save(cv);
    }

}
