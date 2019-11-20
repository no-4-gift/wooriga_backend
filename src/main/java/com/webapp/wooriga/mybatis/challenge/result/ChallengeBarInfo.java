package com.webapp.wooriga.mybatis.challenge.result;

import com.webapp.wooriga.mybatis.vo.Participants;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ChallengeBarInfo {
    private ArrayList<UserInfo> userInfo;
    private ArrayList<String> date;
    private long registeredId;
    private String resolution;
    private String challengeTitle;
    private long challengeId;
    private String challengeImage;
    private int totalNum;
    private int certificationNum;
    private String viewDate;
    public ChallengeBarInfo(){}
    public ChallengeBarInfo(ArrayList<UserInfo> userInfo,String resolution,String challengeImage,long challengeId,long registeredId, String challengeTitle, ArrayList<String> date){
        this.challengeTitle = challengeTitle;
        this.userInfo = userInfo;
        this.resolution = resolution;
        this.date = date;
        this.registeredId = registeredId;
        this.challengeId = challengeId;
        this.challengeImage = challengeImage;
    }
}
