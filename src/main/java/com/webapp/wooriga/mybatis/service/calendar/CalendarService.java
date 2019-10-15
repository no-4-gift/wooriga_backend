package com.webapp.wooriga.mybatis.service.calendar;

import com.webapp.wooriga.mybatis.dao.*;
import com.webapp.wooriga.mybatis.service.result.CommonResult;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import com.webapp.wooriga.mybatis.vo.Participants;
import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;
import com.webapp.wooriga.mybatis.vo.RegisteredDates;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CalendarService {
    private EmptyDaysDAOImpl emptyDaysDAOImpl;
    private RegisteredChallengesDAO registeredChallengesDAO;
    private ParticipantsDAO participantsDAO;
    private RegisteredDatesDAO registeredDatesDAO;

    Logger log = LoggerFactory.getLogger(CalendarService.class);
    @Autowired
    public CalendarService(EmptyDaysDAOImpl emptyDaysDAOImpl, RegisteredChallengesDAO registeredChallengesDAO,
                           ParticipantsDAO participantsDAO, RegisteredDatesDAO registeredDatesDAO){
        this.emptyDaysDAOImpl = emptyDaysDAOImpl;
        this.registeredChallengesDAO = registeredChallengesDAO;
        this.participantsDAO = participantsDAO;
        this.registeredDatesDAO = registeredDatesDAO;
    }

    public CommonResult insertDayOnCalendar(EmptyDays emptyDays){
        CommonResult commonResult = new CommonResult();
        commonResult.setSuccessResult(200,"success");
        try {
            emptyDaysDAOImpl.insertEmptyDay(emptyDays);
        }
        catch(Exception e){
            commonResult.setFailResult(404,"duplicate information");
            log.error(e.toString());
        }
        return commonResult;
    }

    public CommonResult insertRegisteredChallenge(RegisteredChallenges registeredChallenges, Participants[] participants, RegisteredDates[] registeredDates) {
        CommonResult commonResult = new CommonResult();
        commonResult.setSuccessResult(200, "success");
        try {
            registeredChallengesDAO.insertRegisteredChallenge(registeredChallenges);
            long registeredId = registeredChallenges.getRegisteredId();
            for (int i = 0; i < participants.length; i++) {
                participants[i].setRegisteredIdFK(registeredId);
                participantsDAO.insertParticipants(participants[i]);
            }
            for (int i = 0; i < registeredDates.length; i++) {
                registeredDates[i].setRegisteredIdFK(registeredId);
                registeredDatesDAO.insertRegisteredDate(registeredDates[i]);
            }
        } catch (Exception e) {
            commonResult.setFailResult(404, "insert fail");
            log.error(e.toString());
        }
        return commonResult;
    }
}
