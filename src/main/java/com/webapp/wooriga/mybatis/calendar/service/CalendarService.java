package com.webapp.wooriga.mybatis.calendar.service;

import com.webapp.wooriga.mybatis.calendar.result.CalendarInfo;
import com.webapp.wooriga.mybatis.calendar.result.EmptyDayUserInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.vo.EmptyDays;

import java.util.ArrayList;
import java.util.Map;

public interface CalendarService {
    void insertDayOnCalendar(EmptyDays emptyDays) throws RuntimeException;
    CalendarInfo sendCalendarInfo(String familyId, String year, String month) throws RuntimeException;
    void deleteCalendarInfo(EmptyDays emptyDays) throws RuntimeException;
    ArrayList<EmptyDayUserInfo> makeEmptyDayUserInfoList(String familyId, String firstDate, String finalDate);
    ArrayList<ChallengeBarInfo> makeChallengeBarInfoList(String familyId, String firstDate, String finalDate);
}
