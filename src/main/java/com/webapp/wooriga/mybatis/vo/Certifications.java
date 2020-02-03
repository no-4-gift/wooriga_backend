package com.webapp.wooriga.mybatis.vo;

import lombok.*;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certifications {
    private long registeredIdFK;
    private Date registeredDate;
    private String certificationPhoto;
    private int certificationTrue;
    private RegisteredChallenges registeredChallenges;
}
