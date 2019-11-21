package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.calendar.dao.EmptyDaysDAO;

import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.vo.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service
public class ChallengeModuleServiceImpl implements ChallengeModuleService{
    Logger log = LoggerFactory.getLogger(this.getClass());
    private UserDAO userDAO;
    private EmptyDaysDAO emptyDaysDAO;


    public ChallengeModuleServiceImpl(){ }
    @Autowired
    public ChallengeModuleServiceImpl(UserDAO userDAO, EmptyDaysDAO emptyDaysDAO){
        this.userDAO = userDAO;
        this.emptyDaysDAO = emptyDaysDAO;

    }

    @Override
    public Boolean ifCorrectUserIntheFamily(long chiefId, Participants[] participants,String familyId){
        ArrayList<Long> participantsList = new ArrayList<>();
        for(int i = 0; i < participants.length; i++)
            participantsList.add(participants[i].getParticipantFK());
        HashMap<String,Object> familyhashMap = new HashMap<>();
        familyhashMap.put("chiefId",chiefId);
        familyhashMap.put("participantsList",participantsList);
        familyhashMap.put("familyId",familyId);

        int count = userDAO.selectUserToFamilyId(familyhashMap);

        if(count != participants.length + 1)
            throw new NoMatchPointException();
        else
            return true;
    }
    @Override
    public Boolean validateParticipantsNum(int participantsNum){
        if (participantsNum > 6)
            throw new NoMatchPointException();
        else
            return true;
    }
    @Override
    public Boolean validateChallengeDateNum(int dateNum){
        if (dateNum > 10)
            throw new NoMatchPointException();
        return true;
    }
    @Override
    public Boolean ifCorrectParticipants(Certifications[] certifications, RegisteredChallenges registeredChallenges,
                                         Participants[] participants){
        for (int i = 0; i < certifications.length; i++) {
            for (int j = 0; j < participants.length; j++) {
                EmptyDays emptyDays = new EmptyDays(registeredChallenges.getFamilyId(),
                        participants[j].getParticipantFK(), certifications[i].getRegisteredDate());
                int count = emptyDaysDAO.selectToId(emptyDays);
                if (count == 0)
                    throw new NoMatchPointException();
            }
        }
        return true;
    }
    public ArrayList<UserInfo> findUserSetToEmpty(int dateSize,HashMap<String,Object> emptyMap){
        HashMap<Long,Integer> userMap = new HashMap<>();
        List<EmptyDays> emptyDays = emptyDaysDAO.selectToDate(emptyMap);
        for(EmptyDays emptyDay : emptyDays){
            long id = emptyDay.getUserIdFk();
            if(!userMap.isEmpty() && userMap.containsKey(id)){
                int emptyDayNum = userMap.get(id);
                userMap.replace(id,emptyDayNum+1);
            }
            else
                userMap.put(id,1);
        }
        ArrayList<UserInfo> userInfoList = new ArrayList<>();
        Iterator<Long> iterator = userMap.keySet().iterator();
        while(iterator.hasNext()) {
            Long key = iterator.next();
            if (dateSize == userMap.get(key)) {
                User user = userDAO.selectOne(key);
                userInfoList.add(new UserInfo(user.getRelationship(),user.getName(),user.getProfile(),user.getColor(),user.getUid()));
            }
        }
        return userInfoList;
    }
}
