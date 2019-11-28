package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.calendar.service.CalendarModuleService;
import com.webapp.wooriga.mybatis.challenge.dao.*;
import com.webapp.wooriga.mybatis.challenge.result.CertificationInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeDetailInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeViewInfo;
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
    private ChallengeImagesDAO challengeImagesDAO;
    public ChallengeViewServiceImpl() {
    }

    @Autowired
    public ChallengeViewServiceImpl(ChallengeImagesDAO challengeImagesDAO,RegisteredChallengesDAO registeredChallengesDAO,ParticipantsDAO participantsDAO,ChallengesDAO challengesDAO,CertificationsDAO certificationsDAO, CalendarModuleService calendarModuleService) {
        this.calendarModuleService = calendarModuleService;
        this.registeredChallengesDAO = registeredChallengesDAO;
        this.participantsDAO = participantsDAO;
        this.certificationsDAO = certificationsDAO;
        this.challengesDAO = challengesDAO;
        this.challengeImagesDAO = challengeImagesDAO;
    }

    @Override
    public ArrayList<ChallengeViewInfo> sendChallengeViewInfo(Boolean ourTrue, String familyId, long uid) throws RuntimeException {
        ArrayList<ChallengeViewInfo> challengeViewInfoArrayList;
        List<Certifications> certificationsList;
        HashMap<String,Object> infoHashMap = makeInfoHashMap(ourTrue,familyId,uid);
        if (!ourTrue) {
            certificationsList = certificationsDAO.selectMyChallengeViewInfo(infoHashMap);
            challengeViewInfoArrayList = makeChallengeViewInfo(certificationsList,2);
        }
        else {
            certificationsList = certificationsDAO.selectOurChallengeViewInfo(infoHashMap);
            challengeViewInfoArrayList = makeChallengeViewInfo(certificationsList,1);
        }
        return challengeViewInfoArrayList;
    }
    private HashMap<String, Object> makeInfoHashMap(Boolean ourTrue,String familyId, long uid){
        HashMap<String, Object> infoHashMap = new HashMap<>();
        infoHashMap.put("familyId", familyId);
        infoHashMap.put("uid", uid);
        if(ourTrue){
            List<Participants> participantsList = participantsDAO.selectParticipantId(uid);
            if(participantsList.isEmpty()) throw new NoMatchPointException();
            ArrayList<Long> registeredList = new ArrayList<>();
            for(Participants participant : participantsList){
                registeredList.add(participant.getRegisteredIdFK());
            }
            infoHashMap.put("registeredList", registeredList);
        }
        return infoHashMap;
    }
    @Override
    public ArrayList<ChallengeViewInfo> makeChallengeViewInfo(List<Certifications> certificationsList,int pageId){
        ArrayList<ChallengeViewInfo> challengeViewInfoArrayList = new ArrayList<>();
        if (certificationsList.isEmpty()) throw new NoMatchPointException();
        ArrayList<ChallengeBarInfo>  challengeBarInfoArrayList = setCertificationAndTotalNum(calendarModuleService.setChallengeBarInfoList(certificationsList));
        for(ChallengeBarInfo challengeBarInfo : challengeBarInfoArrayList){
            ChallengeImages challengeImages = challengeImagesDAO.selectChallengeId(challengeBarInfo.getChallengeId(),pageId);
            ChallengeViewInfo challengeViewInfo = new ChallengeViewInfo(challengeBarInfo,challengeImages.getImage());
            challengeViewInfoArrayList.add(challengeViewInfo);
        }
        return challengeViewInfoArrayList;
    }

    private ArrayList<ChallengeBarInfo> setCertificationAndTotalNum(ArrayList<ChallengeBarInfo> challengeBarInfoArrayList) throws RuntimeException{
        for (ChallengeBarInfo challengeBarInfo : challengeBarInfoArrayList){
            long registeredId = challengeBarInfo.getRegisteredId();
            int totalNum = certificationsDAO.selectTotalDateNum(registeredId);
            int certificationNum = certificationsDAO.selectCertificationDateNum(registeredId);
            challengeBarInfo.setTotalNum(totalNum);
            challengeBarInfo.setCertificationNum(certificationNum);
            int percentage = (int)(certificationNum/(double)totalNum*100);
            challengeBarInfo.setPercentage(percentage);
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
            CertificationInfo certificationInfo = new CertificationInfo(new SimpleDateFormat("yyyy.MM.dd").format(certifications.getRegisteredDate()) ,new SimpleDateFormat("MM.dd").format(certifications.getRegisteredDate()),
                    certifications.getCertificationPhoto(), certifications.getCertificationTrue());
            certificationInfoArrayList.add(certificationInfo);
        }

        ChallengeDetailInfo challengeDetailInfo = new ChallengeDetailInfo(challenges.getTitle(),certificationInfoArrayList,registeredChallenge.getResolution(),challenges.getSummary(),challenges.getContent());

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
