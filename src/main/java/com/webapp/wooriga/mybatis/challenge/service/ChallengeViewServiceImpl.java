package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.calendar.service.CalendarModuleService;
import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ParticipantsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.RegisteredChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.result.CertificationInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeDetailInfo;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.vo.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ChallengeViewServiceImpl implements ChallengeViewService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private CalendarModuleService calendarModuleService;
    private CertificationsDAO certificationsDAO;
    private ChallengesDAO challengesDAO;
    private RegisteredChallengesDAO registeredChallengesDAO;
    private ParticipantsDAO participantsDAO;
    public ChallengeViewServiceImpl() {
    }

    @Autowired
    public ChallengeViewServiceImpl(RegisteredChallengesDAO registeredChallengesDAO,ParticipantsDAO participantsDAO,ChallengesDAO challengesDAO,CertificationsDAO certificationsDAO, CalendarModuleService calendarModuleService) {
        this.calendarModuleService = calendarModuleService;
        this.registeredChallengesDAO = registeredChallengesDAO;
        this.participantsDAO = participantsDAO;
        this.certificationsDAO = certificationsDAO;
        this.challengesDAO = challengesDAO;
    }

    @Override
    public ArrayList<ChallengeBarInfo> sendChallengeViewInfo(Boolean ourTrue, String familyId, long uid) throws RuntimeException {
        HashMap<String, Object> infoHashMap = new HashMap<>();
        infoHashMap.put("familyId", familyId);
        infoHashMap.put("uid", uid);
        List<Certifications> certificationsList;
        if (!ourTrue)
            certificationsList = certificationsDAO.selectMyChallengeViewInfo(infoHashMap);
        else
            certificationsList = certificationsDAO.selectOurChallengeViewInfo(infoHashMap);

        if (certificationsList.size() > 0)
            return setCertificationAndTotalNum(calendarModuleService.setChallengeBarInfoList(certificationsList));
        else
            throw new NoMatchPointException();
    }

    private ArrayList<ChallengeBarInfo> setCertificationAndTotalNum(ArrayList<ChallengeBarInfo> challengeBarInfoArrayList) throws RuntimeException{
        for (ChallengeBarInfo challengeBarInfo : challengeBarInfoArrayList){
            long registeredId = challengeBarInfo.getRegisteredId();
            challengeBarInfo.setTotalNum(certificationsDAO.selectTotalDateNum(registeredId));
            challengeBarInfo.setCertificationNum(certificationsDAO.selectCertificationDateNum(registeredId));
        }
        return setLatestViewDate(challengeBarInfoArrayList);
    }

    private ArrayList<ChallengeBarInfo> setLatestViewDate(ArrayList<ChallengeBarInfo> challengeBarInfoArrayList) throws RuntimeException{

        for(ChallengeBarInfo challengeBarInfo : challengeBarInfoArrayList){
            long calDateMin = Long.MAX_VALUE;
            HashMap<String,Object> challengeMap = new HashMap<>();
            challengeMap.put("registeredId",challengeBarInfo.getRegisteredId());
            challengeMap.put("dateList",challengeBarInfo.getDate());
           List<Certifications> certificationsList = certificationsDAO.selectNonCertificateDate(challengeMap);
           try {
                   for (Certifications certification : certificationsList) {
                       Date challengeDay = certification.getRegisteredDate();
                       Date today = new Date();
                       long calDate = challengeDay.getTime() - today.getTime();
                       if(calDateMin > Math.abs(calDate/( 24*60*60*1000))){
                           calDateMin = Math.abs(calDate/( 24*60*60*1000));
                           challengeBarInfo.setViewDate(challengeDay.toString());
                       }
                   }
           }catch(Exception e){
               throw new NoMatchPointException();
           }
       }
        return challengeBarInfoArrayList;
    }
    @Override
    public ChallengeDetailInfo sendChallengeDetailInfo(long uid, long registeredId) throws RuntimeException {
        ChallengeDetailInfo challengeDetailInfo = new ChallengeDetailInfo();
        challengeDetailInfo.setCertificationAvailableTrue(false);

        List<Certifications> certificationsList = certificationsDAO.selectChallengeDetailInfo(registeredId);
        if(certificationsList == null) throw new NoMatchPointException();

        boolean participantTrue = false;

        List<Participants> participantsList = participantsDAO.selectParticipants(registeredId);
        RegisteredChallenges registeredChallenge = registeredChallengesDAO.selectRegisteredChallenge(registeredId);

        challengeDetailInfo.setResolution(registeredChallenge.getResolution());
        long challengeId = registeredChallenge.getChallengeIdFK();
        Challenges challenges = challengesDAO.selectChallenge(challengeId);
        challengeDetailInfo.setSummary(challenges.getSummary());
        challengeDetailInfo.setContent(challenges.getContent());

        if(registeredChallenge.getChiefIdFK() == uid) {
            challengeDetailInfo.setCertificationAvailableTrue(true);
            participantTrue = true;
        }

        for(Participants participant : participantsList){
            if(participant.getParticipantFK() == uid)
                participantTrue = true;
        }
        if(!participantTrue) throw new NoMatchPointException();

        ArrayList<CertificationInfo> certificationInfoArrayList = new ArrayList<>();

        for(Certifications certifications : certificationsList){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            CertificationInfo certificationInfo = new CertificationInfo(simpleDateFormat.format(certifications.getRegisteredDate()),
                    certifications.getCertificationPhoto(), certifications.getCertificationTrue());
            certificationInfoArrayList.add(certificationInfo);
        }
        challengeDetailInfo.setCertificationInfoArrayList(certificationInfoArrayList);
        return challengeDetailInfo;
    }


}
