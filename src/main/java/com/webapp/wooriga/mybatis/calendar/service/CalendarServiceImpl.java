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
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class CalendarServiceImpl implements CalendarService {
    private EmptyDaysDAO emptyDaysDAO;
    private UserDAO userDAO;
    private CertificationsDAO certificationsDAO;
    private ChallengesDAO challengesDAO;

    Logger log = LoggerFactory.getLogger(CalendarServiceImpl.class);
    @Autowired
    public CalendarServiceImpl(ChallengesDAO challengesDAO,CertificationsDAO certificationsDAO,EmptyDaysDAO emptyDaysDAO, UserDAO userDAO){
        this.emptyDaysDAO = emptyDaysDAO;
        this.userDAO = userDAO;
        this.certificationsDAO = certificationsDAO;
        this.challengesDAO = challengesDAO;
    }

    @Override
    public void insertDayOnCalendar(EmptyDays emptyDays) throws RuntimeException{
        try {
            User user = userDAO.selectOne(emptyDays.getUserIdFk());
            emptyDaysDAO.insertEmptyDay(emptyDays);
        }
        catch(Exception e) {
            throw new NoStoringException();
        }
    }

    @Override
    public CalendarInfo selectCalendarInfo(Map<String,String> family) throws RuntimeException{
        String firstDate = family.get("year") + "-" + family.get("month") + "-" + "01";
        String finalDate = family.get("year") +"-" +  family.get("month") + "-" + "31";
        String familyId = family.get("familyId");
        CalendarInfo calendarInfo = new CalendarInfo();
        try {
            List<EmptyDays> emptyDaysList = emptyDaysDAO.selectEmptyDay(familyId, firstDate, finalDate);
            if (emptyDaysList.size() > 0) {
                ArrayList<EmptyDayUserInfo> emptyDayUserInfoList = new ArrayList<>();
                int i = 0;
                for (EmptyDays emptyDay : emptyDaysList) {
                    User user = userDAO.selectUserForCalendar(emptyDay);
                    EmptyDayUserInfo emptyDayUserInfo = new EmptyDayUserInfo(emptyDay.getEmptydate().toString(), user.getColor(), user.getName(), user.getRelationship(), user.getProfile());
                    emptyDayUserInfoList.add(emptyDayUserInfo);
                    i++;
                }
                calendarInfo.setEmptyDayUserInfoArrayList(emptyDayUserInfoList);
            }
            List<Certifications> certificationsList = certificationsDAO.selectList(familyId,firstDate,finalDate);
            if(certificationsList.size() > 0){
                ArrayList<ChallengeBarInfo> challengeBarInfoList = new ArrayList<>();
                int i = 0;
                for(Certifications certifications : certificationsList){
                    ChallengeBarInfo challengeBarInfo = new ChallengeBarInfo();
                    RegisteredChallenges registeredChallenges = certifications.getRegisteredChallenges();
                    User user = userDAO.selectOne(registeredChallenges.getChiefIdFK());
                    Challenges challenges = challengesDAO.selectChallenge(registeredChallenges.getChallengeIdFK());
                    challengeBarInfo.setChiefColor(user.getColor());
                    challengeBarInfo.setChiefId(user.getUid());
                    challengeBarInfo.setDate(certifications.getRegisteredDate());
                    challengeBarInfo.setChallengeTitle(challenges.getTitle());
                    challengeBarInfoList.add(challengeBarInfo);
                }
                calendarInfo.setChallengeBarInfo(challengeBarInfoList);
            }
            return calendarInfo;
        }
        catch(Exception e){
            log.error(e.toString());
                throw new NoMatchPointException();
            }
    }

    @Override
    public void deleteCalendarInfo(EmptyDays emptyDays) throws RuntimeException{
        emptyDaysDAO.deleteToId(emptyDays);
    }
}
