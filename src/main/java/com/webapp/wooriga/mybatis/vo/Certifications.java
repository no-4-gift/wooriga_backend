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

    public Certifications(long registeredIdFK, Date registeredDate){
        this.registeredDate = registeredDate;
        this.registeredIdFK = registeredIdFK;
    }
    public Certifications(){

    }
}
