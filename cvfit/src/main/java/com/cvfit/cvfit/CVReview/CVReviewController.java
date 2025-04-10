package com.cvfit.cvfit.CVReview;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/CVReview")
public class CVReviewController {

    private CVReviewService CVReviewService ;



    @PostMapping("/CV")
    public String uploadPdf(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Failed: No file uploaded!";
        }


        if (!file.getContentType().contains("pdf")) {
            return "Failed: Only PDF files are allowed!";
        }

        return CVReviewService.ReviewCV(file);
    }
}
