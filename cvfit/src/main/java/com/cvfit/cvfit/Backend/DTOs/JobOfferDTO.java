package com.cvfit.cvfit.Backend.DTOs;

public class JobOfferDTO {
    private String title;
    private String link;

    public JobOfferDTO(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
