package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.calendar.dao.EmptyDaysDAO;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChallengeModuleService {
    private UserDAO userDAO;
    private EmptyDaysDAO emptyDaysDAO;

    public ChallengeModuleService(){ }
    @Autowired
    public ChallengeModuleService(UserDAO userDAO, EmptyDaysDAO emptyDaysDAO){
        this.userDAO = userDAO;
        this.emptyDaysDAO = emptyDaysDAO;
    }

    public Boolean ifCorrectUserIntheFamily(RegisteredChallenges registeredChallenges){
        int count = userDAO.selectUserToFamilyId(registeredChallenges.getFamilyId());
        if(count != 0)
            return true;
        else
            throw new NoMatchPointException();
    }
    public Boolean validateParticipantsNum(int participantsNum){
        if (participantsNum > 6)
            throw new NoMatchPointException();
        else
            return true;
    }
    public Boolean validateChallengeDateNum(int dateNum){
        if (dateNum > 10)
            throw new NoMatchPointException();
        return true;
    }
    public Boolean ifCorrectParticipants(Certifications[] certifications, RegisteredChallenges registeredChallenges,
                                         Participants[] participants){
        for (int i = 0; i < certifications.length; i++) {
            for (int j = 0; j < participants.length; j++) {
                EmptyDays emptyDays = new EmptyDays(registeredChallenges.getFamilyId(),
                        participants[j].getParticipantFK(), certifications[i].getRegisteredDate());
                int count = emptyDaysDAO.selectToId(emptyDays);
                if (count == 0)
                    throw new NoMatchPointException();
            }
        }
        return true;
    }
    public Boolean ifParticipantsAreCorrectUser(Participants[] participants,long registeredId){
        for (int i = 0; i < participants.length; i++) {
            participants[i].setRegisteredIdFK(registeredId);
            User user = userDAO.selectOne(participants[i].getParticipantFK());
            if (user == null)
                throw new NoMatchPointException();
        }
        return true;
    }
}
