package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.calendar.dao.EmptyDaysDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import com.webapp.wooriga.mybatis.vo.Participants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MakeHashMapServiceImpl implements MakeHashMapService{
    private MakeArrayListService makeArrayListService;
    private EmptyDaysDAO emptyDaysDAO;
    public MakeHashMapServiceImpl(EmptyDaysDAO emptyDaysDAO,MakeArrayListService makeArrayListService){
        this.makeArrayListService = makeArrayListService;
        this.emptyDaysDAO = emptyDaysDAO;
    }
    @Override
    public HashMap<String,Object> makeFamilyHashMap(long chiefId, Participants[] participants, String familyId){
        HashMap<String,Object> familyhashMap = new HashMap<>();
        familyhashMap.put("chiefId",chiefId);
        familyhashMap.put("participantsList",makeArrayListService.makeParticipantsList(participants));
        familyhashMap.put("familyId",familyId);
        return familyhashMap;
    }
    @Override
    public HashMap<String,Object> makeChallengeHashMap(ChallengeBarInfo challengeBarInfo){
        HashMap<String,Object> challengeMap = new HashMap<>();
        challengeMap.put("registeredId",challengeBarInfo.getRegisteredId());
        challengeMap.put("dateList",challengeBarInfo.getDate());
        return challengeMap;
    }
    @Override
    public HashMap<Long,Integer> makeUserHashMap(HashMap<String,Object> emptyMap){
        HashMap<Long,Integer> userMap = new HashMap<>();
        List<EmptyDays> emptyDays = emptyDaysDAO.selectToDate(emptyMap);
        for(EmptyDays emptyDay : emptyDays){
            long id = emptyDay.getUserIdFk();
            if(!userMap.isEmpty() && userMap.containsKey(id))
                userMap.replace(id,userMap.get(id)+1);
            else
                userMap.put(id,1);
        }
        return userMap;
    }
}
