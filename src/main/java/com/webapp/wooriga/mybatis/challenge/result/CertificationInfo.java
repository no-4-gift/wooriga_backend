package com.webapp.wooriga.mybatis.challenge.result;

import lombok.Data;

@Data
public class CertificationInfo {
    private String certificationDate;
    private String certificationImage;
    private int certificationTrue;

    public CertificationInfo(String certificationDate, String certificationImage, int certificationTrue){
        this.certificationDate = certificationDate;
        this.certificationImage = certificationImage;
        this.certificationTrue = certificationTrue;
    }
}
