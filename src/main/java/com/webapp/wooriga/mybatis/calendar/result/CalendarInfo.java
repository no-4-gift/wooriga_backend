package com.webapp.wooriga.mybatis.calendar.result;

import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CalendarInfo {
    private ArrayList<EmptyDayUserInfo> emptyDayUserInfoArrayList;
    private ArrayList<ChallengeBarInfo> challengeBarInfo;
    public CalendarInfo(){}
    public CalendarInfo(ArrayList<EmptyDayUserInfo> emptyDayUserInfoArrayList,ArrayList<ChallengeBarInfo> challengeBarInfo){
        this.challengeBarInfo = challengeBarInfo;
        this.emptyDayUserInfoArrayList = emptyDayUserInfoArrayList;
    }
}
