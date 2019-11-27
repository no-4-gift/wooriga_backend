package com.webapp.wooriga.mybatis.auth.result;

import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class MyRecordInfo {
    private ArrayList<ChallengeBarInfo> challengeBarInfoArrayList;
    private int successNum;
    private int failNum;
    private int percentage;
    public MyRecordInfo(int percentage,ArrayList<ChallengeBarInfo> challengeBarInfoArrayList,int successNum,int failNum){
        this.challengeBarInfoArrayList = challengeBarInfoArrayList;
        this.successNum = successNum;
        this.failNum = failNum;
        this.percentage = percentage;
    }
    public MyRecordInfo(){}
}
