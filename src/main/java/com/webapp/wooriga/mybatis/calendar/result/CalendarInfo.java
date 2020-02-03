package com.webapp.wooriga.mybatis.calendar.result;

import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@Builder
public class CalendarInfo {
    private ArrayList<EmptyDayUserInfo> emptyDayUserInfoArrayList;
    private ArrayList<ChallengeBarInfo> challengeBarInfo;
}
