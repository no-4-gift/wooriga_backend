package com.webapp.wooriga.mybatis.auth.result;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class MyRecordInfo {
    private ArrayList<MyAchievement> myAchievementArrayList;
    private int successNum;
    private int failNum;
    private int totalNum;
    private int percentage;
    private int presentNum;
    public MyRecordInfo( int presentNum,ArrayList<MyAchievement> myAchievementArrayList, int successNum, int failNum, int percentage, int totalNum){
        this.myAchievementArrayList = myAchievementArrayList;
        this.successNum = successNum;
        this.failNum = failNum;
        this.percentage = percentage;
        this.totalNum = totalNum;
        this.presentNum = presentNum;
    }
    public MyRecordInfo(){}
}
