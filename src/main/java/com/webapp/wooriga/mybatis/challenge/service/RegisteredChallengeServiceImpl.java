package com.webapp.wooriga.mybatis.challenge.service;

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

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


@Service
public class RegisteredChallengeServiceImpl implements RegisteredChallengeService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private RegisteredChallengesDAO registeredChallengesDAO;
    private CertificationsDAO certificationsDAO;
    private ParticipantsDAO participantsDAO;
    private ChallengeModuleService challengeModuleService;
    private ImageS3UploadComponent imageS3UploadComponent;

    @Autowired
    public RegisteredChallengeServiceImpl(ImageS3UploadComponent imageS3UploadComponent,ChallengeModuleService challengeModuleService, CertificationsDAO certificationsDAO, RegisteredChallengesDAO registeredChallengesDAO, ParticipantsDAO participantsDAO){
        this.participantsDAO = participantsDAO;
        this.imageS3UploadComponent = imageS3UploadComponent;
        this.registeredChallengesDAO = registeredChallengesDAO;
        this.certificationsDAO = certificationsDAO;
        this.challengeModuleService = challengeModuleService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRegisteredChallenge(RegisteredChallenges registeredChallenges, Participants[] participants, Certifications[] certifications) throws RuntimeException {
        long registeredId;
        try {
            if (challengeModuleService.ifCorrectUserIntheFamily(registeredChallenges.getChiefIdFK(),participants,registeredChallenges.getFamilyId()))
                registeredChallengesDAO.insertRegisteredChallenge(registeredChallenges);

        } catch(Exception e) {
            throw new NoStoringException();
        }
        try {
            registeredId = registeredChallenges.getRegisteredId();
            challengeModuleService.validateParticipantsNum(participants.length);
            challengeModuleService.ifCorrectParticipants(certifications, registeredChallenges, participants);
            challengeModuleService.validateChallengeDateNum(certifications.length);
        }catch(NoMatchPointException e) {
            throw new NoMatchPointException();
        }
        try{
                for (int i = 0; i < certifications.length; i++){
                    certifications[i].setRegisteredIdFK(registeredId);
                    certifications[i].setCertificationPhoto("https://woorigabucket.s3.ap-northeast-2.amazonaws.com/challenge/default.jpg");
                    certifications[i].setCertificationTrue(0);
                    certificationsDAO.insertRegisteredDate(certifications[i]);
                }
                for (int i = 0; i < participants.length; i++) {
                    participants[i].setRegisteredIdFK(registeredId);
                    participantsDAO.insertParticipants(participants[i]);
                }
        }
        catch(Exception e){
            log.error(e.toString());
            throw new NoStoringException();
        }
    }
    @Override
    public void cancelChallengeCertification(Map<String,Object> infoMap) throws RuntimeException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        isThereAnyCertificationRow(infoMap);
        try {
            Certifications certifications = new Certifications();
            Date registeredDate = new Date( simpleDateFormat.parse((String)infoMap.get("date")).getTime());
            certifications.setRegisteredDate(registeredDate);
            int registeredId = (Integer)infoMap.get("registeredId");
            certifications.setRegisteredIdFK(registeredId);
            imageS3UploadComponent.fileDelete("challenge"+Integer.toString(registeredId) + registeredDate);
            certifications.setCertificationPhoto("https://woorigabucket.s3.ap-northeast-2.amazonaws.com/challenge/default.jpg");
            certifications.setCertificationTrue(0);
            certificationsDAO.updateCertification(certifications);
        }catch(Exception e){
            throw new NoMatchPointException();
        }
    }

    private void isThereAnyCertificationRow(Map<String,Object> infoMap) throws RuntimeException{
        HashMap<String,Object> infoHashMap = new HashMap<>();
        infoHashMap.put("date",infoMap.get("date"));
        infoHashMap.put("registeredId",infoMap.get("registeredId"));
        if(certificationsDAO.selectCertificationRow(infoHashMap) == 0) throw new NoMatchPointException();
    }


}
