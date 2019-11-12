package com.webapp.wooriga.mybatis.challenge.result;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
public class ChallengeBarInfo {
    private long chiefId;
    private String chiefColor;
    private Date date;
    private String challengeTitle;

    public ChallengeBarInfo(){}
    public ChallengeBarInfo(long chiefId, String challengeTitle, Date date,String chiefColor){
        this.challengeTitle = challengeTitle;
        this.date = date;
        this.chiefColor = chiefColor;
        this.chiefId = chiefId;
    }
}
