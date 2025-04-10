package com.cvfit.cvfit.Backend.Entities;


import jakarta.persistence.*;

@Entity
@Table(name = "job_offer")
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobOfferId;

    private String title;
    private String link; // URL to job offer

    @ManyToOne
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

    public JobOffer() {}

    public JobOffer(String title, String link, CV cv) {
        this.title = title;
        this.link = link;
        this.cv = cv;
    }

    public Long getJobOfferId() { return jobOfferId; }
    public String getTitle() { return title; }
    public String getLink() { return link; }
    public CV getCv() { return cv; }

    public void setTitle(String title) { this.title = title; }
    public void setLink(String link) { this.link = link; }
    public void setCv(CV cv) { this.cv = cv; }
}
