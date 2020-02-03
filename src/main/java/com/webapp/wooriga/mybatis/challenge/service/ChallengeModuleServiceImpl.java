package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.auth.service.UserService;
import com.webapp.wooriga.mybatis.calendar.dao.EmptyDaysDAO;

import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.vo.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service
public class ChallengeModuleServiceImpl implements ChallengeModuleService{
    Logger log = LoggerFactory.getLogger(this.getClass());
    private UserDAO userDAO;
    private EmptyDaysDAO emptyDaysDAO;
    private UserService userService;
    private MakeResultService makeResultService;
    private MakeHashMapService makeHashMapService;
    private MakeArrayListService makeArrayListService;
    public ChallengeModuleServiceImpl(){ }
    @Autowired
    public ChallengeModuleServiceImpl(MakeArrayListService makeArrayListService,MakeHashMapService makeHashMapService,MakeResultService makeResultService,UserDAO userDAO, EmptyDaysDAO emptyDaysDAO,UserService userService){
        this.userDAO = userDAO;
        this.emptyDaysDAO = emptyDaysDAO;
        this.userService = userService;
        this.makeResultService = makeResultService;
        this.makeHashMapService = makeHashMapService;
        this.makeArrayListService = makeArrayListService;

    }
    @Override
    public Boolean ifCorrectUserIntheFamily(long chiefId, Participants[] participants,String familyId) throws RuntimeException{
        int count = userDAO.selectUserToFamilyId(
                makeHashMapService.makeFamilyHashMap(chiefId,participants,familyId));
        return count == participants.length + 1;
    }
    @Override
    public void validateParticipantsNum(int participantsNum){
        if (participantsNum > 6)
            throw new NoMatchPointException();
    }
    @Override
    public void validateChallengeDateNum(int dateNum){
        if (dateNum > 10)
            throw new NoMatchPointException();
    }

    @Override
    public void ifCorrectParticipants(Certifications[] certifications, RegisteredChallenges registeredChallenges,
                                         Participants[] participants){
        for (Certifications certification : certifications) {
            for (Participants participant : participants) {
                if (emptyDaysDAO.selectToId(makeResultService.makeEmptyDays(registeredChallenges.getFamilyId(),participant.getParticipantFK(),certification.getRegisteredDate())) == 0)
                    throw new NoMatchPointException();
            }
        }
    }
    public ArrayList<UserInfo> findUserSetToEmpty(int dateSize,HashMap<String,Object> emptyMap){
        return makeArrayListService.makeUserInfoArrayList(makeHashMapService.makeUserHashMap(emptyMap),dateSize);
    }

}
