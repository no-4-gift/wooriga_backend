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

import java.util.*;

@Transactional
@Service
public class CalendarServiceImpl implements CalendarService {
    private EmptyDaysDAO emptyDaysDAO;
    private CertificationsDAO certificationsDAO;
    private UserDAO userDAO;
    private CalendarModuleService calendarModuleService;

    Logger log = LoggerFactory.getLogger(CalendarServiceImpl.class);
    @Autowired
    public CalendarServiceImpl(UserDAO userDAO,CalendarModuleService calendarModuleService,CertificationsDAO certificationsDAO,EmptyDaysDAO emptyDaysDAO){
        this.emptyDaysDAO = emptyDaysDAO;
        this.userDAO = userDAO;
        this.certificationsDAO = certificationsDAO;
        this.calendarModuleService = calendarModuleService;
    }

    @Override
    @Transactional
    public void insertDayOnCalendar(EmptyDays emptyDays) throws RuntimeException{
        List<User> userList = Optional.of(userDAO.selectfamilyId(emptyDays.getFamilyId())).orElseThrow(NoMatchPointException::new);
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
        return CalendarInfo.builder()
                .challengeBarInfo(makeChallengeBarInfoList(familyId,firstDate,finalDate))
                .emptyDayUserInfoArrayList(makeEmptyDayUserInfoList(familyId,firstDate,finalDate))
                .build();
    }
    @Override
    public void deleteCalendarInfo(EmptyDays emptyDays) throws RuntimeException{
        if(emptyDaysDAO.selectToId(emptyDays) == 0) throw new NoMatchPointException();
        emptyDaysDAO.deleteToId(emptyDays);
    }
    @Override
    public ArrayList<EmptyDayUserInfo> makeEmptyDayUserInfoList(String familyId,String firstDate, String finalDate){
        List<EmptyDays> emptyDaysList = emptyDaysDAO.selectEmptyDay(familyId, firstDate, finalDate);
        return !emptyDaysList.isEmpty() ? calendarModuleService.setEmptyDayUserInfoList(emptyDaysList)
                : new ArrayList<>();
    }
    @Override
    public ArrayList<ChallengeBarInfo> makeChallengeBarInfoList(String familyId,String firstDate,String finalDate){
        List<Certifications> certificationsList = certificationsDAO.selectList(familyId,firstDate,finalDate);
        return !certificationsList.isEmpty() ? calendarModuleService.setChallengeBarInfoList(certificationsList)
                : new ArrayList<>();
    }
}
