package com.webapp.wooriga.mybatis.calendar.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.auth.service.UserService;
import com.webapp.wooriga.mybatis.calendar.result.EmptyDayUserInfo;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ParticipantsDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.challenge.service.MakeArrayListService;
import com.webapp.wooriga.mybatis.challenge.service.MakeResultService;
import com.webapp.wooriga.mybatis.exception.NoInformationException;
import com.webapp.wooriga.mybatis.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CalendarModuleServiceImpl implements CalendarModuleService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private UserDAO userDAO;
    private ChallengesDAO challengesDAO;
    private ParticipantsDAO participantsDAO;
    private UserService userService;
    private MakeResultService makeResultService;
    private MakeArrayListService makeArrayListService;
    public CalendarModuleServiceImpl() { }

    @Autowired
    public CalendarModuleServiceImpl(MakeArrayListService makeArrayListService,MakeResultService makeResultService,UserService userService,ParticipantsDAO participantsDAO,ChallengesDAO challengesDAO, UserDAO userDAO) {
        this.userDAO = userDAO;
        this.userService = userService;
        this.challengesDAO = challengesDAO;
        this.participantsDAO = participantsDAO;
        this.makeResultService = makeResultService;
        this.makeArrayListService = makeArrayListService;
    }
    @Override
    public ArrayList<EmptyDayUserInfo> setEmptyDayUserInfoList(List<EmptyDays> emptyDaysList) {
        ArrayList<EmptyDayUserInfo> emptyDayUserInfoList = new ArrayList<>();
        for (EmptyDays emptyDay : emptyDaysList) {
            User user = userDAO.selectUserForCalendar(emptyDay);
            emptyDayUserInfoList.add(makeEmptyDayUserInfo(user,emptyDay.getEmptydate().toString()));
        }
        return emptyDayUserInfoList;
    }
    private EmptyDayUserInfo makeEmptyDayUserInfo(User user, String emptydate){
        return EmptyDayUserInfo.builder()
                .userInfo(makeResultService.makeUserInfo(user))
                .emptyDate(emptydate)
                .build();
    }

    @Override
    public ArrayList<ChallengeBarInfo> setChallengeBarInfoList(List<Certifications> certificationsList){
        ArrayList<ChallengeBarInfo> challengeBarInfoList = new ArrayList<>();
        HashMap<Long,ChallengeBarInfo> challengeBarInfoHashMap = this.setDateListWithHashMap(certificationsList);
        for(Long key : challengeBarInfoHashMap.keySet())
            challengeBarInfoList.add(challengeBarInfoHashMap.get(key));
        return challengeBarInfoList;
    }

    @Override
    public HashMap<Long, ChallengeBarInfo> returnChallengeBarInfoThatHasRegisteredId(HashMap<Long, ChallengeBarInfo> challengeBarInfoHashMap, long registeredId, Date registeredDate){
        ChallengeBarInfo challengeBarInfo = challengeBarInfoHashMap.get(registeredId);
        challengeBarInfo.setDate(this.makeDateListInRegisteredDate(registeredDate));
        challengeBarInfoHashMap.replace(registeredId,challengeBarInfo);
        return challengeBarInfoHashMap;
    }
    @Override
    public HashMap<Long, ChallengeBarInfo> setDateListWithHashMap(List<Certifications> certificationsList) {
        HashMap<Long, ChallengeBarInfo> challengeBarInfoHashMap = new HashMap<>();
        for (Certifications certifications : certificationsList) {
            long registeredId = certifications.getRegisteredIdFK();
            if (challengeBarInfoHashMap.containsKey(registeredId))
                challengeBarInfoHashMap = this.returnChallengeBarInfoThatHasRegisteredId(challengeBarInfoHashMap,registeredId,certifications.getRegisteredDate());
            else
                challengeBarInfoHashMap.put(registeredId, this.setChallengeBar(certifications,
                        registeredId,certifications.getRegisteredChallenges()));
        }
        return challengeBarInfoHashMap;
    }
    @Override
    public ArrayList<String> makeDateListInRegisteredDate(Date registeredDate){
        ArrayList<String> dateList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateList.add(simpleDateFormat.format(registeredDate));
        return dateList;
    }
    @Override
    public ChallengeBarInfo setChallengeBar(Certifications certifications,long registeredId, RegisteredChallenges registeredChallenges){
        User chief = userDAO.selectOne(registeredChallenges.getChiefIdFK());
        Challenges challenges = Optional.of(challengesDAO.selectChallenge(registeredChallenges.getChallengeIdFK())).orElseThrow(NoInformationException::new);
        return setChallengeTitlePlusName(makeResultService.makeChallengeBarInfo(
                registeredChallenges,chief,registeredId,challenges,certifications,
                this.makeUserInfoArrayListAndMerge(chief,registeredId),
                this.makeDateListInRegisteredDate(certifications.getRegisteredDate())
                ));
    }

    @Override
    public ArrayList<UserInfo> setParticipantsInfo(long registeredId) throws RuntimeException{
        List<Participants> participantsList = participantsDAO.selectParticipants(registeredId);
        ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();
        for(Participants participant : participantsList)
            userInfoArrayList = makeArrayListService.convertUserToUserInfoAndAddArrayList(userService.loadUserByUsername(participant.getParticipantFK()),userInfoArrayList);
        return userService.sortwithUserName(userInfoArrayList);
    }
    private ChallengeBarInfo setChallengeTitlePlusName(ChallengeBarInfo challengeBarInfo){
       String challengeTitle = challengeBarInfo.getChallengeTitle();
       String title = "";
       int count = 0;
       for(UserInfo participant : challengeBarInfo.getUserInfo()){
           title = count < 2 ? count > 0 ? title + "/" : title + participant.getName() : title;
           count++;
       }
       title = count <= 2 ? title + "과(와) \n" + challengeTitle : title + " 등 \n" + count + "명과 " + challengeTitle;
       challengeBarInfo.setChallengeTitle(title);
       return challengeBarInfo;
    }
    @Override
    public ArrayList<UserInfo> makeUserInfoArrayListAndMerge(User chief,long registeredId)  {
        ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();
        userInfoArrayList = makeArrayListService.convertUserToUserInfoAndAddArrayList(chief,userInfoArrayList);
        userInfoArrayList.addAll(this.setParticipantsInfo(registeredId));
        return userInfoArrayList;
    }

}
