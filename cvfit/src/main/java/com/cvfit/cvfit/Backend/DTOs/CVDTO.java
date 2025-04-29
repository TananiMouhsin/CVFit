package com.cvfit.cvfit.Backend.DTOs;

public class CVDTO {

    private String pdfCv;
    private Long Cv_id;


    public CVDTO(String pdfCv, Long Cv_id ) {

        this.pdfCv = pdfCv;
        this.Cv_id = Cv_id; 
    }

    public String getPdfCv() {
        return pdfCv;
    }

    public Long getCv_id() {
        return Cv_id;
    }
    

}
