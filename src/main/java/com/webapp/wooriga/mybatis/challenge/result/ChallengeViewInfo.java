package com.webapp.wooriga.mybatis.challenge.result;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ChallengeViewInfo {
    private ArrayList<ChallengeBarInfo> challengeBarInfoArrayList;
    private int totalNum;
    private int certificationNum;
    public ChallengeViewInfo(){}
    public ChallengeViewInfo(ArrayList<ChallengeBarInfo> challengeBarInfoArrayList,int totalNum,int certificationNum){
        this.challengeBarInfoArrayList = challengeBarInfoArrayList;
        this.totalNum = totalNum;
        this.certificationNum = certificationNum;
    }
}
