package com.cvfit.cvfit.Backend.Entities;


import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String title;

    @ManyToOne
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

    public Role() {}

    public Role(String title, CV cv) {
        this.title = title;
        this.cv = cv;
    }

    public Long getRoleId() { return roleId; }
    public String getTitle() { return title; }
    public CV getCv() { return cv; }

    public void setTitle(String title) { this.title = title; }
    public void setCv(CV cv) { this.cv = cv; }
}

