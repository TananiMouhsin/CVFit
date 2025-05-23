package com.cvfit.cvfit.Backend.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cv")
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cvId;
    private String cvName;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String pdfCv; // File path or URL
    @Column(columnDefinition = "LONGTEXT")
    private String strengths;
    
    @Column(columnDefinition = "LONGTEXT")
    private String enhancements;
     

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobOffer> jobOffers;

    public CV() {}

    public CV(User user, String cvName, String pdfCv, String strengths, String enhancements) {
        this.user = user;
        this.cvName = cvName;
        this.pdfCv = pdfCv;
        this.strengths = strengths;
        this.enhancements = enhancements;
    }

    public Long getCvId() { return cvId; }
    public String getCvName() { return cvName; }
    public User getUser() { return user; }
    public String getPdfCv() { return pdfCv; }
    public String getStrengths() { return strengths; }
    public String getEnhancements() { return enhancements; }

    public void setCvName(String cvName) { this.cvName = cvName; }
    public void setUser(User user) { this.user = user; }
    public void setPdfCv(String pdfCv) { this.pdfCv = pdfCv; }
    public void setStrengths(String strengths) { this.strengths = strengths; }
    public void setEnhancements(String enhancements) { this.enhancements = enhancements; }
}
