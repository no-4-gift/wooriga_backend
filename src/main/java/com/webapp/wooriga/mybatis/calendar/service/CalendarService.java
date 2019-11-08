package com.webapp.wooriga.mybatis.calendar.service;

import com.webapp.wooriga.mybatis.calendar.result.CalendarInfo;
import com.webapp.wooriga.mybatis.vo.EmptyDays;

import java.util.Map;

public interface CalendarService {
    void insertDayOnCalendar(EmptyDays emptyDays) throws RuntimeException;
    CalendarInfo[] selectCalendarInfo(Map<String,String> family) throws RuntimeException;
}
