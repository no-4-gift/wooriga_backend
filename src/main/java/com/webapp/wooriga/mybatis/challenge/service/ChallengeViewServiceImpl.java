package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.auth.service.UserService;
import com.webapp.wooriga.mybatis.calendar.service.CalendarModuleService;
import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ParticipantsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.RegisteredChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.result.CertificationInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeDetailInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.vo.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ChallengeViewServiceImpl implements ChallengeViewService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private CalendarModuleService calendarModuleService;
    private CertificationsDAO certificationsDAO;
    private ChallengesDAO challengesDAO;
    private ParticipantsDAO participantsDAO;
    private RegisteredChallengesDAO registeredChallengesDAO;
    private UserDAO userDAO;
    private UserService userService;
    public ChallengeViewServiceImpl() {
    }

    @Autowired
    public ChallengeViewServiceImpl(UserService userService,UserDAO userDAO,ParticipantsDAO participantsDAO,ChallengesDAO challengesDAO,CertificationsDAO certificationsDAO, CalendarModuleService calendarModuleService,RegisteredChallengesDAO registeredChallengesDAO) {
        this.calendarModuleService = calendarModuleService;
        this.userService = userService;
        this.certificationsDAO = certificationsDAO;
        this.challengesDAO = challengesDAO;
        this.registeredChallengesDAO = registeredChallengesDAO;
        this.participantsDAO = participantsDAO;
        this.userDAO = userDAO;
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
        List<Certifications> certificationsList = certificationsDAO.selectChallengeDetailInfo(registeredId);
        if(certificationsList == null) throw new NoMatchPointException();
        ArrayList<CertificationInfo> certificationInfoArrayList = new ArrayList<>();
        for(Certifications certifications : certificationsList) {
            RegisteredChallenges registeredChallenges = certifications.getRegisteredChallenges();
            challengeDetailInfo.setResolution(registeredChallenges.getResolution());
            if (uid != registeredChallenges.getChiefIdFK())
                challengeDetailInfo.setCertificationAvailableTrue(false);
            else
                challengeDetailInfo.setCertificationAvailableTrue(true);
            long challengeId = registeredChallenges.getChallengeIdFK();
            Challenges challenges = challengesDAO.selectChallenge(challengeId);
            challenges.setChallengeId(challengeId);
            challengeDetailInfo.setChallenges(challenges);
            break;
        }
        for(Certifications certifications : certificationsList){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            CertificationInfo certificationInfo = new CertificationInfo(simpleDateFormat.format(certifications.getRegisteredDate()),certifications.getCertificationPhoto(),
            certifications.getCertificationTrue());
            certificationInfoArrayList.add(certificationInfo);
        }
        challengeDetailInfo.setCertificationInfoArrayList(certificationInfoArrayList);
        return challengeDetailInfo;
    }
    @Override
    public ArrayList<UserInfo> sendParticipantsInfo(long registeredId){
        ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();
        List<Long> userIdList = new ArrayList<>();
        Long chiefId = registeredChallengesDAO.selectRegisteredCertification(registeredId);
        if(chiefId == null) throw new NoMatchPointException();
        User user = userDAO.selectOne(chiefId);
        UserInfo chiefInfo = new UserInfo(user.getRelationship(),user.getName(),user.getProfile(),user.getColor(),user.getUid());
        userInfoArrayList.add(chiefInfo);
        List<Participants> participantsList = participantsDAO.selectParticipants(registeredId);
        for(Participants participants : participantsList)
            userIdList.add(participants.getParticipantFK());
        List<User> userList = userDAO.selectUserId(userIdList);
        userInfoArrayList.addAll(userService.sortwithUserName(userList));

        return userInfoArrayList;
    }

}
