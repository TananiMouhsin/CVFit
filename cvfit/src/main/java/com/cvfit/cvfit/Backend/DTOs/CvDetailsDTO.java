package com.cvfit.cvfit.Backend.DTOs;

public class CvDetailsDTO {
    private String strengths;
    private String enhancements;


    public CvDetailsDTO() {}

    public CvDetailsDTO(String strengths, String enhancements) {
        this.strengths = strengths;
        this.enhancements = enhancements;
    }

    
    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getEnhancements() {
        return enhancements;
    }

    public void setEnhancements(String enhancements) {
        this.enhancements = enhancements;
    }
}
