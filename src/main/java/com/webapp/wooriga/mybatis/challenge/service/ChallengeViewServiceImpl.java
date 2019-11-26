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
        else {
            List<Participants> participantsList = participantsDAO.selectParticipantId(uid);
            if(participantsList.isEmpty()) throw new NoMatchPointException();
            ArrayList<Long> registeredList = new ArrayList<>();
            for(Participants participant : participantsList){
                registeredList.add(participant.getRegisteredIdFK());
            }
            infoHashMap.put("registeredList", registeredList);
            certificationsList = certificationsDAO.selectOurChallengeViewInfo(infoHashMap);
        }
        if (certificationsList.size() > 0)
            return setCertificationAndTotalNum(calendarModuleService.setChallengeBarInfoList(certificationsList));
        else
            throw new NoMatchPointException();
    }

    private ArrayList<ChallengeBarInfo> setCertificationAndTotalNum(ArrayList<ChallengeBarInfo> challengeBarInfoArrayList) throws RuntimeException{
        for (ChallengeBarInfo challengeBarInfo : challengeBarInfoArrayList){
            long registeredId = challengeBarInfo.getRegisteredId();
            int totalNum = certificationsDAO.selectTotalDateNum(registeredId);
            int certificationNum = certificationsDAO.selectCertificationDateNum(registeredId);
            challengeBarInfo.setTotalNum(totalNum);
            challengeBarInfo.setCertificationNum(certificationNum);
            challengeBarInfo.setPercentage(certificationNum/totalNum*100);
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
        List<Certifications> certificationsList = certificationsDAO.selectChallengeDetailInfo(registeredId);
        if(certificationsList.isEmpty()) throw new NoMatchPointException();
        RegisteredChallenges registeredChallenge = registeredChallengesDAO.selectRegisteredChallenge(registeredId);
        log.error(Long.toString(registeredChallenge.getChallengeIdFK()));
        long challengeId = registeredChallenge.getChallengeIdFK();
        Challenges challenges = challengesDAO.selectChallenge(challengeId);
        ArrayList<CertificationInfo> certificationInfoArrayList = new ArrayList<>();

        for(Certifications certifications : certificationsList) {
            CertificationInfo certificationInfo = new CertificationInfo(new SimpleDateFormat("MM.dd").format(certifications.getRegisteredDate()),
                    certifications.getCertificationPhoto(), certifications.getCertificationTrue());
            certificationInfoArrayList.add(certificationInfo);
        }

        ChallengeDetailInfo challengeDetailInfo = new ChallengeDetailInfo(certificationInfoArrayList,registeredChallenge.getResolution(),challenges.getSummary(),challenges.getContent());

        return setUserIsCertificationAvailable(uid,registeredChallenge.getChiefIdFK(),challengeDetailInfo,registeredId);
    }

    private ChallengeDetailInfo setUserIsCertificationAvailable(long uid, long chiefId, ChallengeDetailInfo challengeDetailInfo,long registeredId){
        if(chiefId == uid)
            challengeDetailInfo.setCertificationAvailableTrue(true);
        else {
            int count = participantsDAO.selectUserIsCorrectParticipant(registeredId, uid);
            challengeDetailInfo.setCertificationAvailableTrue(false);
            if (count == 0) throw new NoMatchPointException();
        }
        return challengeDetailInfo;
    }


}
