package com.cvfit.cvfit.CVRoles;
import com.cvfit.cvfit.CVReview.CVReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/CVRoles")
public class CVRolesController {

    private CVRolesService CVRolesService ;

    
    @PostMapping("/GetRoles")
    public String[] GetRoles(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return new String[]{ "Failed: No file uploaded!"};
        }


        if (!file.getContentType().contains("pdf")) {
            return new String[]{ "Failed: Only PDF files are allowed!"};
        }

        return CVRolesService.GetRoles(file);
    }

    @PostMapping("/Scrap")
    public List<Map<String, String>> ScrapData(@RequestParam("file") MultipartFile file) {
        // if (city == null || city.isEmpty() || role == null || role.isEmpty()) {
        //     return new String[]{"Failed: City and role cannot be empty!"};
        // }

        String[] Roles = GetRoles(file);
        System.out.println("Rolesssss : " );
        List<Map<String, String>> jobList = new ArrayList<>(); 
        for(String role:Roles){
            System.out.println(role);
            List<Map<String, String>> jobs = CVRolesService.Scrape(role) ;
            jobList.addAll(jobs);
            
        }
        System.out.println(jobList.size());
        return jobList ; 
        // return CVRolesService.Scrape(role);
    }

    
}
