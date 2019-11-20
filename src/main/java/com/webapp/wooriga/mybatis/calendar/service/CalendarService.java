package com.webapp.wooriga.mybatis.calendar.service;

import com.webapp.wooriga.mybatis.calendar.result.CalendarInfo;
import com.webapp.wooriga.mybatis.vo.EmptyDays;

import java.util.Map;

public interface CalendarService {
    void insertDayOnCalendar(EmptyDays emptyDays) throws RuntimeException;
    CalendarInfo sendCalendarInfo(String familyId, String year, String month) throws RuntimeException;
    void deleteCalendarInfo(EmptyDays emptyDays) throws RuntimeException;
}
