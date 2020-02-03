package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class Certifications {
    private long registeredIdFK;
    private Date registeredDate;
    private String certificationPhoto;
    private int certificationTrue;
    private RegisteredChallenges registeredChallenges;

}
