package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.calendar.service.CalendarModuleService;
import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeDetailInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.vo.Challenges;
import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ChallengeViewServiceImpl implements ChallengeViewService {
    private CalendarModuleService calendarModuleService;
    private CertificationsDAO certificationsDAO;
    private ChallengesDAO challengesDAO;

    public ChallengeViewServiceImpl() {
    }

    @Autowired
    public ChallengeViewServiceImpl(ChallengesDAO challengesDAO,CertificationsDAO certificationsDAO, CalendarModuleService calendarModuleService) {
        this.calendarModuleService = calendarModuleService;
        this.certificationsDAO = certificationsDAO;
        this.challengesDAO = challengesDAO;
    }

    @Override
    public ArrayList<ChallengeBarInfo> sendChallengeViewInfo(Boolean ourTrue, String familyId, long uid) throws RuntimeException {
        HashMap<String, Object> infoHashMap = new HashMap<>();
        infoHashMap.put("familyId", familyId);
        infoHashMap.put("uid", uid);
        List<Certifications> certificationsList;
        if (!ourTrue) {
            certificationsList = certificationsDAO.selectMyChallengeViewInfo(infoHashMap);
        } else {
            certificationsList = certificationsDAO.selectOurChallengeViewInfo(infoHashMap);
        }
        if (certificationsList.size() > 0)
            return setCertificationAndTotalNum(certificationsList, calendarModuleService.setChallengeBarInfoList(true, certificationsList));
        else
            throw new NoMatchPointException();
    }

    private ArrayList<ChallengeBarInfo> setCertificationAndTotalNum(List<Certifications> certificationsList, ArrayList<ChallengeBarInfo> challengeBarInfoArrayList) {
        HashMap<Long,Integer> certificationNum = new HashMap<>();
        HashMap<Long,Integer> totalNum = new HashMap<>();
        for (Certifications certifications : certificationsList) {
            long registeredId = certifications.getRegisteredIdFK();
            if (!totalNum.containsKey(registeredId)) {
                totalNum.put(registeredId, 1);
                if (certifications.getCertificationTrue() == 1)
                    certificationNum.put(registeredId, 1);
            } else {
                int num = totalNum.get(registeredId);
                totalNum.replace(registeredId, num + 1);
                if (certificationNum.containsKey(registeredId) && certifications.getCertificationTrue() == 1) {
                    num = certificationNum.get(registeredId);
                    certificationNum.replace(registeredId, num + 1);
                } else if (certifications.getCertificationTrue() == 1)
                    certificationNum.put(registeredId, 1);
            }
        }
        for(ChallengeBarInfo challengeBarInfo : challengeBarInfoArrayList){
            long registeredId = challengeBarInfo.getRegisteredId();
            if(certificationNum.containsKey(registeredId))
                challengeBarInfo.setCertificationNum(certificationNum.get(registeredId));
            challengeBarInfo.setTotalNum(totalNum.get(registeredId));
        }
        return challengeBarInfoArrayList;
    }

    @Override
    public ChallengeDetailInfo sendChallengeDetailInfo(long uid, long registeredId){
        ChallengeDetailInfo challengeDetailInfo = new ChallengeDetailInfo();
        ArrayList<Certifications> certificationsList = (ArrayList<Certifications>)certificationsDAO.selectChallengeDetailInfo(registeredId);
        if(certificationsList == null) throw new NoMatchPointException();

        for(Certifications certifications : certificationsList) {
            RegisteredChallenges registeredChallenges = certifications.getRegisteredChallenges();
            challengeDetailInfo.setResolution(registeredChallenges.getResolution());
            if (uid != registeredChallenges.getChiefIdFK())
                challengeDetailInfo.setCertificationAvailableTrue(false);
            else
                challengeDetailInfo.setCertificationAvailableTrue(true);
            long challengeId = registeredChallenges.getChallengeIdFK();
            Challenges challenges = challengesDAO.selectChallenge(challengeId);
            challengeDetailInfo.setChallenges(challenges);
            break;
        }
        challengeDetailInfo.setCertificationsArrayList(certificationsList);
        return challengeDetailInfo;
    }
    @Override
    public ArrayList<UserInfo> sendParticipantsInfo(long registeredId){
        ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();

        return userInfoArrayList;
    }

}
