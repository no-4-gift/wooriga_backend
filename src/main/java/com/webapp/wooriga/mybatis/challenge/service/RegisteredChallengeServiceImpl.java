package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.calendar.dao.EmptyDaysDAOImpl;
import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ParticipantsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.RegisteredChallengesDAO;
import com.webapp.wooriga.mybatis.exception.NoStoringException;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.vo.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RegisteredChallengeServiceImpl implements RegisteredChallengeService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private RegisteredChallengesDAO registeredChallengesDAO;
    private CertificationsDAO certificationsDAO;
    private ParticipantsDAO participantsDAO;
    private UserDAO userDAO;
    private EmptyDaysDAOImpl emptyDaysDAOImpl;


    @Autowired
    public RegisteredChallengeServiceImpl(EmptyDaysDAOImpl emptyDaysDAOImpl, UserDAO userDAO, CertificationsDAO certificationsDAO, RegisteredChallengesDAO registeredChallengesDAO, ParticipantsDAO participantsDAO){
        this.participantsDAO = participantsDAO;
        this.registeredChallengesDAO = registeredChallengesDAO;
        this.certificationsDAO = certificationsDAO;
        this.userDAO = userDAO;
        this.emptyDaysDAOImpl = emptyDaysDAOImpl;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRegisteredChallenge(RegisteredChallenges registeredChallenges, Participants[] participants, Certifications[] certifications) throws RuntimeException {
        long registeredId;
        try {
            if (ifCorrectUserIntheFamily(registeredChallenges))
                registeredChallengesDAO.insertRegisteredChallenge(registeredChallenges);
        } catch(Exception e) {
            throw new NoStoringException();
        }
        try {
            registeredId = registeredChallenges.getRegisteredId();
            validateParticipantsNum(participants.length);
            ifCorrectParticipants(certifications, registeredChallenges, participants);
            ifParticipantsAreCorrectUser(participants, registeredId);
            validateChallengeDateNum(certifications.length);
        }catch(NoMatchPointException e) {
            throw new NoMatchPointException();
        }
        try{
                for (int i = 0; i < certifications.length; i++) {
                    certifications[i].setRegisteredIdFK(registeredId);
                    certifications[i].setCertificationPhoto("https://woorigabucket.s3.ap-northeast-2.amazonaws.com/challenge/default.jpg");
                    certificationsDAO.insertRegisteredDate(certifications[i]);
                }
                for (int i = 0; i < participants.length; i++) {
                    participants[i].setRegisteredIdFK(registeredId);
                    participantsDAO.insertParticipants(participants[i]);
                }
        }
        catch(Exception e){
            throw new NoStoringException();
        }
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
    public Boolean ifCorrectParticipants(Certifications[] certifications,RegisteredChallenges registeredChallenges,
                                       Participants[] participants){
        for (int i = 0; i < certifications.length; i++) {
            for (int j = 0; j < participants.length; j++) {
                EmptyDays emptyDays = new EmptyDays(null,registeredChallenges.getFamilyId(),
                        participants[j].getParticipantFK(), certifications[i].getRegisteredDate());
                int count = emptyDaysDAOImpl.selectToId(emptyDays);
                if (count == 0) {
                    log.error(emptyDays.getEmptydate().toString() + " " + Long.toString(emptyDays.getUserIdFk()));
                    throw new NoMatchPointException();
                }
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
