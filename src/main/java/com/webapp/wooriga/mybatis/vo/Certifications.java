package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Certifications {
    private long registeredIdFK;
    private Date registeredDate;
    private String certificationPhoto;
    private int certificationTrue;
    private RegisteredChallenges registeredChallenges;

    public Certifications(long registeredIdFK, Date registeredDate,int certificationTrue,String certificationPhoto){
        this.registeredDate = registeredDate;
        this.registeredIdFK = registeredIdFK;
        this.certificationTrue = certificationTrue;
        this.certificationPhoto = certificationPhoto;
    }
    public Certifications(){

    }
}
