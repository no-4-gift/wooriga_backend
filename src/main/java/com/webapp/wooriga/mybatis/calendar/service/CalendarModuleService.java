package com.webapp.wooriga.mybatis.calendar.service;

import com.webapp.wooriga.mybatis.calendar.result.EmptyDayUserInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.vo.EmptyDays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface CalendarModuleService {
    ArrayList<EmptyDayUserInfo> setEmptyDayUserInfoList(List<EmptyDays> emptyDaysList);
    ArrayList<ChallengeBarInfo> setChallengeBarInfoList(List<Certifications> certificationsList);
    ArrayList<UserInfo> setParticipantsInfo(long registeredId);
}
