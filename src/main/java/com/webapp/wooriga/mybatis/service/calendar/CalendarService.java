package com.webapp.wooriga.mybatis.service.calendar;

import com.webapp.wooriga.mybatis.controller.result.CalendarInfo;
import com.webapp.wooriga.mybatis.dao.*;
import com.webapp.wooriga.mybatis.vo.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class CalendarService {
    private EmptyDaysDAOImpl emptyDaysDAOImpl;
    private RegisteredChallengesDAO registeredChallengesDAO;
    private ParticipantsDAO participantsDAO;
    private RegisteredDatesDAO registeredDatesDAO;
    private UserDAO userDAO;

    Logger log = LoggerFactory.getLogger(CalendarService.class);
    @Autowired
    public CalendarService(EmptyDaysDAOImpl emptyDaysDAOImpl, RegisteredChallengesDAO registeredChallengesDAO,
                           ParticipantsDAO participantsDAO, RegisteredDatesDAO registeredDatesDAO, UserDAO userDAO){
        this.emptyDaysDAOImpl = emptyDaysDAOImpl;
        this.registeredChallengesDAO = registeredChallengesDAO;
        this.participantsDAO = participantsDAO;
        this.registeredDatesDAO = registeredDatesDAO;
        this.userDAO = userDAO;
    }

    public void insertDayOnCalendar(EmptyDays emptyDays, HttpServletResponse httpServletResponse){
        httpServletResponse.setStatus(200);
        try {
            emptyDaysDAOImpl.insertEmptyDay(emptyDays);
        }
        catch(Exception e) {
            //duplicate date
            httpServletResponse.setStatus(400);
            log.error(e.toString());
        }
    }

    public void insertRegisteredChallenge(RegisteredChallenges registeredChallenges, Participants[] participants, RegisteredDates[] registeredDates
    ,HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(200);
        try {
            registeredChallengesDAO.insertRegisteredChallenge(registeredChallenges);
            long registeredId = registeredChallenges.getRegisteredId();
            if(participants.length > 6){
                //챌린지 최대 참여 인원수 6 넘음
                httpServletResponse.setStatus(402);
                return;
            }
            for (int i = 0; i < participants.length; i++) {
                participants[i].setRegisteredIdFK(registeredId);
                participantsDAO.insertParticipants(participants[i]);
            }
            if(registeredDates.length > 10) {
                //챌린지 신청 최대 일수 10 넘음
                httpServletResponse.setStatus(401);
                return;
            }
            for (int i = 0; i < registeredDates.length; i++) {
                registeredDates[i].setRegisteredIdFK(registeredId);
                registeredDatesDAO.insertRegisteredDate(registeredDates[i]);
            }
        } catch (Exception e) {
            //insert fail
            httpServletResponse.setStatus(400);
            log.error(e.toString());
        }
    }

    public CalendarInfo[] selectCalendarInfo(Map<String,String> family, HttpServletResponse response){
        response.setStatus(200);
        String firstDate = family.get("year") + "-" + family.get("month") + "-" + "01";
        String finalDate = family.get("year") +"-" +  family.get("month") + "-" + "31";
        String familyId = family.get("familyId");
        try {
            List<EmptyDays> emptyDays = emptyDaysDAOImpl.selectEmptyDay(familyId, firstDate, finalDate);
            if (emptyDays.size() > 0) {
                CalendarInfo[] calendarInfos = new CalendarInfo[emptyDays.size()];
                int i = 0;
                for (EmptyDays emptyDay : emptyDays) {
                    User user = userDAO.selectUserForCalendar(emptyDay);
                    calendarInfos[i] = new CalendarInfo(emptyDay.getEmptydate().toString(), user.getColor(), user.getName(), user.getRelationship(), user.getProfile());
                    i++;
                }
                return calendarInfos;
            }
            else{
                //정보가 없음
                response.setStatus(300);
                return null;
            }
        }
        catch(Exception e){
            //실패
                response.setStatus(400);
                log.error(e.toString());
            }
        return null;
    }
}
