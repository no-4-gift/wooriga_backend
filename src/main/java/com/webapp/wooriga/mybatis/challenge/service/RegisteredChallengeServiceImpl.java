package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ParticipantsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.RegisteredChallengesDAO;
import com.webapp.wooriga.mybatis.exception.NoInformationException;
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
import java.util.Optional;


@Service
public class RegisteredChallengeServiceImpl implements RegisteredChallengeService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private RegisteredChallengesDAO registeredChallengesDAO;
    private CertificationsDAO certificationsDAO;
    private ParticipantsDAO participantsDAO;
    private ChallengeModuleService challengeModuleService;
    private ImageS3UploadComponent imageS3UploadComponent;
    private ChallengeService challengeService;
    private MakeResultService makeResultService;
    private static String DEFAULT_IMAGE = "https://woorigabucket.s3.ap-northeast-2.amazonaws.com/challenge/default.jpg";

    @Autowired
    public RegisteredChallengeServiceImpl(MakeResultService makeResultService,ChallengeService challengeService,ImageS3UploadComponent imageS3UploadComponent,ChallengeModuleService challengeModuleService, CertificationsDAO certificationsDAO, RegisteredChallengesDAO registeredChallengesDAO, ParticipantsDAO participantsDAO){
        this.participantsDAO = participantsDAO;
        this.challengeService = challengeService;
        this.imageS3UploadComponent = imageS3UploadComponent;
        this.registeredChallengesDAO = registeredChallengesDAO;
        this.certificationsDAO = certificationsDAO;
        this.challengeModuleService = challengeModuleService;
        this.makeResultService = makeResultService;
    }
    @Override
    public void validateRegisteredChallenge(RegisteredChallenges registeredChallenges, Participants[] participants, Certifications[] certifications) throws RuntimeException{
        try {
            challengeModuleService.validateParticipantsNum(participants.length);
            challengeModuleService.ifCorrectParticipants(certifications, registeredChallenges, participants);
            challengeModuleService.validateChallengeDateNum(certifications.length);
        }catch(NoMatchPointException e) {
            throw new NoMatchPointException();
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void registerChallenge(RegisteredChallenges registeredChallenges, Participants[] participants, Certifications[] certifications) throws RuntimeException{
        Long registeredId = Optional.of(registeredChallenges.getRegisteredId()).orElseThrow(NoInformationException::new);
        this.insertRegisteredChallenge(registeredChallenges, participants, certifications);
        this.validateRegisteredChallenge(registeredChallenges, participants, certifications);
        this.initializeCertificationsForChallenge(certifications,registeredId);
        this.initializeParticipantsForChallenge(participants, registeredId);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void initializeParticipantsForChallenge(Participants[] participants, long registeredId) throws RuntimeException{
        try{
            for (Participants participant : participants) {
                participant.setRegisteredIdFK(registeredId);
                participantsDAO.insertParticipants(participant);
            }
        }
        catch(Exception e){
            log.error(e.toString());
            throw new NoStoringException();
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void initializeCertificationsForChallenge(Certifications[] certifications,long registeredId) throws RuntimeException{
        try{
            for (Certifications certification : certifications){
                certification = makeResultService.makeCertifications(certification.getRegisteredDate().toString(),
                        0,registeredId,DEFAULT_IMAGE);
                certificationsDAO.insertRegisteredDate(certification);
            }
        }
        catch(Exception e){
            log.error(e.toString());
            throw new NoStoringException();
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRegisteredChallenge(RegisteredChallenges registeredChallenges, Participants[] participants, Certifications[] certifications) throws RuntimeException {
        try {
            if (challengeModuleService.ifCorrectUserIntheFamily(registeredChallenges.getChiefIdFK(),participants,registeredChallenges.getFamilyId()))
                registeredChallengesDAO.insertRegisteredChallenge(registeredChallenges);
            else throw new NoInformationException();
        } catch(Exception e) {
            throw new NoStoringException();
        }

    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initializeChallengeCertification(Long registeredId,Date registeredDate) throws RuntimeException{
            challengeService.updateCertification(makeResultService
                    .makeCertifications(registeredDate.toString(),0,registeredId
                    ,DEFAULT_IMAGE));
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelChallengeCertification(Map<String,Object> infoMap) throws RuntimeException{
        isThereAnyCertificationRow(infoMap);
        try {
            Long registeredId = Optional.of((Long)infoMap.get("registeredId")).orElseThrow(NoMatchPointException::new);
            Date registeredDate = new Date( new SimpleDateFormat("yyyy-MM-dd").parse((String)infoMap.get("date")).getTime());
            this.initializeChallengeCertification(registeredId,registeredDate);
            imageS3UploadComponent.fileDelete("challenge"+registeredId + registeredDate);
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
