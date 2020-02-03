package com.webapp.wooriga.mybatis.calendar.service;

import com.webapp.wooriga.mybatis.calendar.result.EmptyDayUserInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.vo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface CalendarModuleService {
    ArrayList<EmptyDayUserInfo> setEmptyDayUserInfoList(List<EmptyDays> emptyDaysList);

    ArrayList<ChallengeBarInfo> setChallengeBarInfoList(List<Certifications> certificationsList);

    ArrayList<UserInfo> makeUserInfoArrayListAndMerge(User chief, long registeredId);

    ArrayList<UserInfo> setParticipantsInfo(long registeredId);

    ChallengeBarInfo setChallengeBar(Certifications certifications, long registeredId, RegisteredChallenges registeredChallenges);

    ArrayList<String> makeDateListInRegisteredDate(Date registeredDate);

    HashMap<Long, ChallengeBarInfo> returnChallengeBarInfoThatHasRegisteredId(HashMap<Long, ChallengeBarInfo> challengeBarInfoHashMap, long registeredId, Date registeredDate);

    HashMap<Long, ChallengeBarInfo> setDateListWithHashMap(List<Certifications> certificationsList);
}
