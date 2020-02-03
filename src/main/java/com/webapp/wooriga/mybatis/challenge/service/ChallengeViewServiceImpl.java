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

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ChallengeViewServiceImpl implements ChallengeViewService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private CalendarModuleService calendarModuleService;
    private CertificationsDAO certificationsDAO;
    private ChallengesDAO challengesDAO;
    private RegisteredChallengesDAO registeredChallengesDAO;
    private ParticipantsDAO participantsDAO;
    private ChallengeImagesDAO challengeImagesDAO;
    private MakeResultService makeResultService;
    private MakeHashMapService makeHashMapService;
    private MakeArrayListService makeArrayListService;
    public ChallengeViewServiceImpl() {
    }

    @Autowired
    public ChallengeViewServiceImpl(MakeArrayListService makeArrayListService,MakeHashMapService makeHashMapService,MakeResultService makeResultService,ChallengeImagesDAO challengeImagesDAO,RegisteredChallengesDAO registeredChallengesDAO,ParticipantsDAO participantsDAO,ChallengesDAO challengesDAO,CertificationsDAO certificationsDAO, CalendarModuleService calendarModuleService) {
        this.makeArrayListService = makeArrayListService;
        this.calendarModuleService = calendarModuleService;
        this.registeredChallengesDAO = registeredChallengesDAO;
        this.participantsDAO = participantsDAO;
        this.certificationsDAO = certificationsDAO;
        this.challengesDAO = challengesDAO;
        this.challengeImagesDAO = challengeImagesDAO;
        this.makeResultService = makeResultService;
        this.makeHashMapService = makeHashMapService;
    }

    @Override
    public ArrayList<ChallengeViewInfo> sendChallengeViewInfo(Boolean ourTrue, String familyId, long uid) throws RuntimeException {
        HashMap<String,Object> infoHashMap = makeInfoHashMap(ourTrue,familyId,uid);
        List<Certifications> certificationsList = ourTrue ? certificationsDAO.selectOurChallengeViewInfo(infoHashMap)
                : certificationsDAO.selectMyChallengeViewInfo(infoHashMap);
        return ourTrue ? makeChallengeViewInfo(certificationsList,1)
                :makeChallengeViewInfo(certificationsList,2);
    }
    @Override
    public List<Participants> returnParticipantsList(long uid) throws RuntimeException{
        List<Participants> participantsList = participantsDAO.selectParticipantId(uid);
        if(participantsList.isEmpty()) throw new NoMatchPointException();
        return participantsList;
    }
    @Override
    public ArrayList<Long> returnParticipantsIdList(List<Participants> participantsList){
        ArrayList<Long> registeredList = new ArrayList<>();
        for(Participants participant : participantsList)
            registeredList.add(participant.getRegisteredIdFK());
        return registeredList;
    }
    private HashMap<String, Object> makeInfoHashMap(Boolean ourTrue,String familyId, long uid){
        HashMap<String, Object> infoHashMap = new HashMap<>();
        infoHashMap.put("familyId", familyId);
        infoHashMap.put("uid", uid);
        if(!ourTrue) return infoHashMap;
        infoHashMap.put("registeredList", returnParticipantsIdList(returnParticipantsList(uid)));
        return infoHashMap;
    }
    @Override
    public ArrayList<ChallengeViewInfo> makeChallengeViewInfo(List<Certifications> certificationsList,int pageId){
        if (certificationsList.isEmpty()) throw new NoMatchPointException();
        return this.makeChallengeViewInfoArrayList(
                this.makeChallengeBarInfoArrayList(certificationsList)
                ,pageId);
    }
    public ArrayList<ChallengeBarInfo>  makeChallengeBarInfoArrayList(List<Certifications> certificationsList) throws RuntimeException{
        ArrayList<ChallengeBarInfo>  challengeBarInfoArrayList = setCertificationAndTotalNum(
                calendarModuleService.setChallengeBarInfoList(certificationsList));
        if(challengeBarInfoArrayList.isEmpty()) throw new NoMatchPointException();
        return challengeBarInfoArrayList;
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
            List<Certifications> certificationsList = certificationsDAO.selectNonCertificateDate(
                    makeHashMapService.makeChallengeHashMap(challengeBarInfo));
            try {
                long calDateMin = Long.MAX_VALUE;
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
    public ArrayList<ChallengeViewInfo> makeChallengeViewInfoArrayList(ArrayList<ChallengeBarInfo>  challengeBarInfoArrayList,int pageId) throws RuntimeException{
        ArrayList<ChallengeViewInfo> challengeViewInfoArrayList = new ArrayList<>();
        for(ChallengeBarInfo challengeBarInfo : challengeBarInfoArrayList){
            ChallengeImages challengeImages = Optional.of(challengeImagesDAO.selectChallengeId(challengeBarInfo.getChallengeId(),pageId))
                    .orElseThrow(NoMatchPointException::new);
            challengeViewInfoArrayList.add(makeResultService.makeChallengeViewInfo(challengeBarInfo,challengeImages.getImage()));
        }
        return challengeViewInfoArrayList;
    }

    @Override
    public ChallengeDetailInfo sendChallengeDetailInfo(long uid, long registeredId) throws RuntimeException {
        List<Certifications> certificationsList = makeArrayListService.makeCertificationsList(registeredId);
        RegisteredChallenges registeredChallenge = registeredChallengesDAO.selectRegisteredChallenge(registeredId);
        return setUserIsCertificationAvailable(uid,registeredChallenge.getChiefIdFK(),makeResultService
                .makeChallengeDetailInfo(
                        challengesDAO.selectChallenge(
                        registeredChallenge.getChallengeIdFK())
                        ,makeArrayListService.makeCertificationInfoArrayList(certificationsList)
                        ,registeredChallenge),registeredId);
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
