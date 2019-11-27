package com.webapp.wooriga.mybatis.calendar.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.calendar.dao.EmptyDaysDAO;
import com.webapp.wooriga.mybatis.calendar.result.CalendarInfo;
import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengeImagesDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeViewInfo;
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
    private UserDAO userDAO;
    private CalendarModuleService calendarModuleService;
    private ChallengeImagesDAO challengeImagesDAO;

    Logger log = LoggerFactory.getLogger(CalendarServiceImpl.class);
    @Autowired
    public CalendarServiceImpl(ChallengeImagesDAO challengeImagesDAO,UserDAO userDAO,CalendarModuleService calendarModuleService,CertificationsDAO certificationsDAO,EmptyDaysDAO emptyDaysDAO){
        this.emptyDaysDAO = emptyDaysDAO;
        this.userDAO = userDAO;
        this.certificationsDAO = certificationsDAO;
        this.calendarModuleService = calendarModuleService;
        this.challengeImagesDAO = challengeImagesDAO;
    }

    @Override
    public void insertDayOnCalendar(EmptyDays emptyDays) throws RuntimeException{
        List<User> userList= userDAO.selectfamilyId(emptyDays.getFamilyId());
        if(userList.isEmpty()) throw new NoMatchPointException();
        try{
            emptyDaysDAO.insertEmptyDay(emptyDays);
        }
        catch(Exception e) {
            throw new NoStoringException();
        }
    }

    @Override
    public CalendarInfo sendCalendarInfo(String familyId, String year, String month) throws RuntimeException{
        String firstDate = year + "-" + month + "-" + "01";
        String finalDate = year +"-" +  month + "-" + "31";
        CalendarInfo calendarInfo = new CalendarInfo();
        List<EmptyDays> emptyDaysList = emptyDaysDAO.selectEmptyDay(familyId, firstDate, finalDate);
        ArrayList<EmptyDayUserInfo> emptyDayUserInfoList = new ArrayList<>();
        ArrayList<ChallengeBarInfo> challengeBarInfoList = new ArrayList<>();

        if(!emptyDaysList.isEmpty())
                emptyDayUserInfoList = calendarModuleService.setEmptyDayUserInfoList(emptyDaysList);
        calendarInfo.setEmptyDayUserInfoArrayList(emptyDayUserInfoList);

        List<Certifications> certificationsList = certificationsDAO.selectList(familyId,firstDate,finalDate);
        if(!certificationsList.isEmpty())
            challengeBarInfoList = calendarModuleService.setChallengeBarInfoList(certificationsList);
        calendarInfo.setChallengeBarInfo(challengeBarInfoList);

        return calendarInfo;
    }
    @Override
    public void deleteCalendarInfo(EmptyDays emptyDays) throws RuntimeException{
        if(emptyDaysDAO.selectToId(emptyDays) == 0) throw new NoMatchPointException();
        emptyDaysDAO.deleteToId(emptyDays);

    }

}
