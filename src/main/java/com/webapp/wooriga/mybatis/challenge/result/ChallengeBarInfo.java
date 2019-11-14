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
    private ArrayList<Date> date;
    private long registeredId;
    private String resolution;
    private String challengeTitle;
    private long challengeId;
    private String challengeImage;
    private int totalNum;
    private int certificationNum;
    public ChallengeBarInfo(){}
    public ChallengeBarInfo(String resolution,String challengeImage,long challengeId,long registeredId,long chiefId, String challengeTitle, ArrayList<Date> date,String chiefColor){
        this.challengeTitle = challengeTitle;
        this.resolution = resolution;
        this.date = date;
        this.registeredId = registeredId;
        this.chiefColor = chiefColor;
        this.chiefId = chiefId;
        this.challengeId = challengeId;
        this.challengeImage = challengeImage;
    }
}
