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
    private int challengeId;
    private String challengeTitle;
    private int totalNum;
    private int certificationNum;
    private int percentage;
    private String viewDate;
    public ChallengeBarInfo(){}
    public ChallengeBarInfo(int challengeId,ArrayList<UserInfo> userInfo,String resolution,long registeredId, String challengeTitle, ArrayList<String> date){
        this.challengeTitle = challengeTitle;
        this.challengeId = challengeId;
        this.userInfo = userInfo;
        this.resolution = resolution;
        this.date = date;
        this.registeredId = registeredId;

    }
}
