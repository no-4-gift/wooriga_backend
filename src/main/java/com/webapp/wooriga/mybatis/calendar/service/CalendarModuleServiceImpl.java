package com.webapp.wooriga.mybatis.calendar.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.auth.service.UserService;
import com.webapp.wooriga.mybatis.calendar.result.EmptyDayUserInfo;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ParticipantsDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CalendarModuleServiceImpl implements CalendarModuleService {
    private UserDAO userDAO;
    private ChallengesDAO challengesDAO;
    private ParticipantsDAO participantsDAO;
    private UserService userService;

    public CalendarModuleServiceImpl() { }

    @Autowired
    public CalendarModuleServiceImpl(UserService userService,ParticipantsDAO participantsDAO,ChallengesDAO challengesDAO, UserDAO userDAO) {
        this.userDAO = userDAO;
        this.userService = userService;
        this.challengesDAO = challengesDAO;
        this.participantsDAO = participantsDAO;
    }
    @Override
    public ArrayList<EmptyDayUserInfo> setEmptyDayUserInfoList(List<EmptyDays> emptyDaysList) {
        ArrayList<EmptyDayUserInfo> emptyDayUserInfoList = new ArrayList<>();
        for (EmptyDays emptyDay : emptyDaysList) {
            User user = userDAO.selectUserForCalendar(emptyDay);
            EmptyDayUserInfo emptyDayUserInfo = new EmptyDayUserInfo(new UserInfo(user.getRelationship(),user.getName(),
                    user.getProfile(),user.getColor(),user.getUid()),emptyDay.getEmptydate().toString());
            emptyDayUserInfoList.add(emptyDayUserInfo);
        }
        return emptyDayUserInfoList;
    }

    private HashMap<Long, ChallengeBarInfo> setDateListWithHashMap(List<Certifications> certificationsList) {
        HashMap<Long, ChallengeBarInfo> challengeBarInfoHashMap = new HashMap<>();
        for (Certifications certifications : certificationsList) {

            RegisteredChallenges registeredChallenges = certifications.getRegisteredChallenges();
            long registeredId = certifications.getRegisteredIdFK();

            if (challengeBarInfoHashMap.containsKey(registeredId)) {
                ChallengeBarInfo challengeBarInfo = challengeBarInfoHashMap.get(registeredId);
                ArrayList<String> dateList = challengeBarInfo.getDate();
                dateList.add(new SimpleDateFormat("yyyy-MM-dd").format(certifications.getRegisteredDate()));
                challengeBarInfo.setDate(dateList);
                challengeBarInfoHashMap.replace(registeredId,challengeBarInfo);
            }
           else
                challengeBarInfoHashMap.put(registeredId, this.setChallengeBar(certifications,
                        registeredId,registeredChallenges));
        }
        return challengeBarInfoHashMap;
    }
    private ChallengeBarInfo setChallengeBar(Certifications certifications,long registeredId, RegisteredChallenges registeredChallenges){
        User chief = userDAO.selectOne(registeredChallenges.getChiefIdFK());

        ArrayList<UserInfo> userInfoArrayList= new ArrayList<>();
        userInfoArrayList.add(new UserInfo(chief.getRelationship(),chief.getName(),chief.getProfile(),chief.getColor(),chief.getUid()));
        userInfoArrayList.addAll(this.setParticipantsInfo(registeredId));

        Challenges challenges = challengesDAO.selectChallenge(registeredChallenges.getChallengeIdFK());
        ArrayList<String> dateList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateList.add(simpleDateFormat.format(certifications.getRegisteredDate()));
        return setChallengeTitlePlusName(new ChallengeBarInfo(userInfoArrayList,registeredChallenges.getResolution(),
                challenges.getImage(),registeredChallenges.getChallengeIdFK(),
                registeredId, challenges.getTitle(), dateList));
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
    public ArrayList<UserInfo> setParticipantsInfo(long registeredId){
        List<Participants> participantsList = participantsDAO.selectParticipants(registeredId);
        ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();
        for(Participants participant : participantsList){
            User participantUser = userDAO.selectOne(participant.getParticipantFK());
            UserInfo userInfo = new UserInfo(participantUser.getRelationship(),participantUser.getName(),participantUser.getProfile(),participantUser.getColor(),participantUser.getUid());
            userInfoArrayList.add(userInfo);
        }
        userInfoArrayList.sort((arg0,arg1)->{
            String name0 = arg0.getName();
            String name1 = arg1.getName();
            return userService.sortName(name0,name1);
        });
        return userInfoArrayList;
    }
    private ChallengeBarInfo setChallengeTitlePlusName(ChallengeBarInfo challengeBarInfo){
       String challengeTitle = challengeBarInfo.getChallengeTitle();
       String title = "";
       int count = 0;
       for(UserInfo participant : challengeBarInfo.getUserInfo()){
           if (count < 2) {
               if(count > 0) title += ",";
               title += participant.getName();
           }
           count++;
       }
       if(count <= 2) {
           title += "과(와) ";
           title += challengeTitle;
       }
       else if(count > 2)
           title = title + "외 " + Integer.toString(count - 2) + "명과 " + challengeTitle;
       challengeBarInfo.setChallengeTitle(title);
       return challengeBarInfo;
    }
}
