package com.webapp.wooriga.mybatis.calendar.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.calendar.result.EmptyDayUserInfo;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CalendarModuleServiceImpl implements CalendarModuleService {
    private UserDAO userDAO;
    private ChallengesDAO challengesDAO;

    public CalendarModuleServiceImpl() { }

    @Autowired
    public CalendarModuleServiceImpl(ChallengesDAO challengesDAO, UserDAO userDAO) {
        this.userDAO = userDAO;
        this.challengesDAO = challengesDAO;
    }
    @Override
    public ArrayList<EmptyDayUserInfo> setEmptyDayUserInfoList(List<EmptyDays> emptyDaysList) {
        ArrayList<EmptyDayUserInfo> emptyDayUserInfoList = new ArrayList<>();
        for (EmptyDays emptyDay : emptyDaysList) {
            User user = userDAO.selectUserForCalendar(emptyDay);
            EmptyDayUserInfo emptyDayUserInfo = new EmptyDayUserInfo(emptyDay.getEmptydate().toString(), user.getColor(), user.getName(), user.getRelationship(), user.getProfile());
            emptyDayUserInfoList.add(emptyDayUserInfo);
        }
        return emptyDayUserInfoList;
    }
    @Override
    public HashMap<Long, ChallengeBarInfo> setChallengeInfoHashMap(Boolean viewTrue,List<Certifications> certificationsList) {
        HashMap<Long, ChallengeBarInfo> challengeBarInfoHashMap = new HashMap<>();
        for (Certifications certifications : certificationsList) {
            RegisteredChallenges registeredChallenges = certifications.getRegisteredChallenges();
            long registeredId = certifications.getRegisteredIdFK();

            if (challengeBarInfoHashMap.containsKey(registeredId)) {
                ChallengeBarInfo challengeBarInfo = challengeBarInfoHashMap.get(registeredId);
                ArrayList<Date> dateList = challengeBarInfo.getDate();
                dateList.add(certifications.getRegisteredDate());
                challengeBarInfo.setDate(dateList);
                challengeBarInfoHashMap.remove(registeredId);
                challengeBarInfoHashMap.put(registeredId, challengeBarInfo);
            }
           else
                challengeBarInfoHashMap.put(registeredId, this.setChallengeBar(viewTrue,certifications,
                        registeredId,registeredChallenges));
        }
        return challengeBarInfoHashMap;
    }
    private ChallengeBarInfo setChallengeBar(Boolean viewTrue,Certifications certifications,long registeredId, RegisteredChallenges registeredChallenges){
        User user = userDAO.selectOne(registeredChallenges.getChiefIdFK());
        Challenges challenges = challengesDAO.selectChallenge(registeredChallenges.getChallengeIdFK());
        ArrayList<Date> dateList = new ArrayList<>();
        dateList.add(certifications.getRegisteredDate());
        if(!viewTrue)
        return new ChallengeBarInfo(null,challenges.getChallengeId(),registeredId, registeredChallenges.getChiefIdFK(), challenges.getTitle(), dateList, user.getColor());
        else
            return new ChallengeBarInfo(challenges.getImage(),challenges.getChallengeId(),registeredId, registeredChallenges.getChiefIdFK(), challenges.getTitle(), dateList, user.getColor());
    }

    @Override
    public ArrayList<ChallengeBarInfo> setChallengeBarInfoList(List<Certifications> certificationsList){
        ArrayList<ChallengeBarInfo> challengeBarInfoList = new ArrayList<>();
        HashMap<Long,ChallengeBarInfo> challengeBarInfoHashMap = this.setChallengeInfoHashMap(false,certificationsList);
        for(Long key : challengeBarInfoHashMap.keySet())
            challengeBarInfoList.add(challengeBarInfoHashMap.get(key));
        return challengeBarInfoList;
    }
}
