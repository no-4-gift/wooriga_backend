package com.webapp.wooriga.mybatis.calendar.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.calendar.dao.EmptyDaysDAO;
import com.webapp.wooriga.mybatis.calendar.result.CalendarInfo;
import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.exception.NoStoringException;
import com.webapp.wooriga.mybatis.vo.*;
import com.webapp.wooriga.mybatis.calendar.result.EmptyDayUserInfo;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class CalendarServiceImpl implements CalendarService {
    private EmptyDaysDAO emptyDaysDAO;
    private CertificationsDAO certificationsDAO;
    private CalendarModuleService calendarModuleService;

    Logger log = LoggerFactory.getLogger(CalendarServiceImpl.class);
    @Autowired
    public CalendarServiceImpl(CalendarModuleService calendarModuleService,CertificationsDAO certificationsDAO,EmptyDaysDAO emptyDaysDAO){
        this.emptyDaysDAO = emptyDaysDAO;
        this.certificationsDAO = certificationsDAO;
        this.calendarModuleService = calendarModuleService;
    }

    @Override
    public void insertDayOnCalendar(EmptyDays emptyDays) throws RuntimeException{
        try {
            emptyDaysDAO.insertEmptyDay(emptyDays);
        }
        catch(Exception e) {
            throw new NoStoringException();
        }
    }

    @Override
    public CalendarInfo selectCalendarInfo(String familyId, String year, String month) throws RuntimeException{
        String firstDate = year + "-" + month + "-" + "01";
        String finalDate = year +"-" +  month + "-" + "31";
        CalendarInfo calendarInfo = new CalendarInfo();
            List<EmptyDays> emptyDaysList = emptyDaysDAO.selectEmptyDay(familyId, firstDate, finalDate);
            if(emptyDaysList.size() > 0) {
                ArrayList<EmptyDayUserInfo> emptyDayUserInfoList = calendarModuleService.setEmptyDayUserInfoList(emptyDaysList);
                calendarInfo.setEmptyDayUserInfoArrayList(emptyDayUserInfoList);
            }
            else throw new NoMatchPointException();
        List<Certifications> certificationsList = certificationsDAO.selectList(familyId,firstDate,finalDate);
        if(certificationsList.size() > 0)
            calendarInfo.setChallengeBarInfo(calendarModuleService.setChallengeBarInfoList(false,certificationsList));
        else
            calendarInfo.setChallengeBarInfo(null);

        return calendarInfo;
    }

    @Override
    public void deleteCalendarInfo(EmptyDays emptyDays) throws RuntimeException{
        emptyDaysDAO.deleteToId(emptyDays);
    }

}
