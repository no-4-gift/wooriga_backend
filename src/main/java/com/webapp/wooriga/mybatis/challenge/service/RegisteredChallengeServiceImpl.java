package com.webapp.wooriga.mybatis.challenge.service;

import com.google.gson.JsonObject;
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

import java.util.Map;


@Service
public class RegisteredChallengeServiceImpl implements RegisteredChallengeService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private RegisteredChallengesDAO registeredChallengesDAO;
    private CertificationsDAO certificationsDAO;
    private ParticipantsDAO participantsDAO;
    private ChallengeModuleService challengeModuleService;


    @Autowired
    public RegisteredChallengeServiceImpl(ChallengeModuleService challengeModuleService ,CertificationsDAO certificationsDAO, RegisteredChallengesDAO registeredChallengesDAO, ParticipantsDAO participantsDAO){
        this.participantsDAO = participantsDAO;
        this.registeredChallengesDAO = registeredChallengesDAO;
        this.certificationsDAO = certificationsDAO;
        this.challengeModuleService = challengeModuleService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRegisteredChallenge(RegisteredChallenges registeredChallenges, Participants[] participants, Certifications[] certifications) throws RuntimeException {
        long registeredId;
        try {
            if (challengeModuleService.ifCorrectUserIntheFamily(registeredChallenges))
                registeredChallengesDAO.insertRegisteredChallenge(registeredChallenges);
        } catch(Exception e) {
            throw new NoStoringException();
        }
        try {
            registeredId = registeredChallenges.getRegisteredId();
            challengeModuleService.validateParticipantsNum(participants.length);
            challengeModuleService.ifCorrectParticipants(certifications, registeredChallenges, participants);
            challengeModuleService.ifParticipantsAreCorrectUser(participants, registeredId);
            challengeModuleService.validateChallengeDateNum(certifications.length);
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

    @Override
    public String conveyResolution(Map<String,Object> info) throws RuntimeException{
        RegisteredChallenges registeredChallenges = new RegisteredChallenges();
        registeredChallenges.setFamilyId((String)info.get("familyId"));
        registeredChallenges.setRegisteredId((int)info.get("registeredId"));
        String resolution = registeredChallengesDAO.selectResolution(registeredChallenges);
        if(resolution == null) throw new NoMatchPointException();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("resolution",resolution);
        jsonObject.toString();
        return jsonObject.toString();
    }

}
