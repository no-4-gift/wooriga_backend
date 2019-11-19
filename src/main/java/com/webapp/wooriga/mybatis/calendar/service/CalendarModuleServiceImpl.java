package com.webapp.wooriga.mybatis.calendar.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.auth.service.UserService;
import com.webapp.wooriga.mybatis.calendar.result.EmptyDayUserInfo;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ParticipantsDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.ParticipantsInfo;
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
            EmptyDayUserInfo emptyDayUserInfo = new EmptyDayUserInfo(user.getUid(),emptyDay.getEmptydate().toString(), user.getColor(), user.getName(), user.getRelationship(), user.getProfile());
            emptyDayUserInfoList.add(emptyDayUserInfo);
        }
        return emptyDayUserInfoList;
    }
    @Override
    public HashMap<Long, ChallengeBarInfo> setChallengeInfoHashMap(List<Certifications> certificationsList) {
        HashMap<Long, ChallengeBarInfo> challengeBarInfoHashMap = new HashMap<>();
        for (Certifications certifications : certificationsList) {
            RegisteredChallenges registeredChallenges = certifications.getRegisteredChallenges();
            long registeredId = certifications.getRegisteredIdFK();

            if (challengeBarInfoHashMap.containsKey(registeredId)) {
                ChallengeBarInfo challengeBarInfo = challengeBarInfoHashMap.get(registeredId);
                ArrayList<String> dateList = challengeBarInfo.getDate();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateList.add(simpleDateFormat.format(certifications.getRegisteredDate()));
                challengeBarInfo.setDate(dateList);
                challengeBarInfoHashMap.remove(registeredId);
                challengeBarInfoHashMap.put(registeredId, challengeBarInfo);
            }
           else
                challengeBarInfoHashMap.put(registeredId, this.setChallengeBar(certifications,
                        registeredId,registeredChallenges));
        }
        return challengeBarInfoHashMap;
    }
    private ChallengeBarInfo setChallengeBar(Certifications certifications,long registeredId, RegisteredChallenges registeredChallenges){
        User chief = userDAO.selectOne(registeredChallenges.getChiefIdFK());
        ArrayList<ParticipantsInfo> participantsInfoArrayList= new ArrayList<>();
        participantsInfoArrayList.add(new ParticipantsInfo(chief.getRelationship(),chief.getColor(),chief.getUid(),chief.getProfile(),chief.getName()));
        participantsInfoArrayList.addAll(this.setParticipantsInfo(registeredId));
        Challenges challenges = challengesDAO.selectChallenge(registeredChallenges.getChallengeIdFK());
        ArrayList<String> dateList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateList.add(simpleDateFormat.format(certifications.getRegisteredDate()));
        return setChallengeTitlePlusName(new ChallengeBarInfo(participantsInfoArrayList,registeredChallenges.getResolution(),
                challenges.getImage(),registeredChallenges.getChallengeIdFK(),
                registeredId, challenges.getTitle(), dateList));
    }

    @Override
    public ArrayList<ChallengeBarInfo> setChallengeBarInfoList(List<Certifications> certificationsList){
        ArrayList<ChallengeBarInfo> challengeBarInfoList = new ArrayList<>();
        HashMap<Long,ChallengeBarInfo> challengeBarInfoHashMap = this.setChallengeInfoHashMap(certificationsList);
        for(Long key : challengeBarInfoHashMap.keySet())
            challengeBarInfoList.add(challengeBarInfoHashMap.get(key));
        return challengeBarInfoList;
    }
    @Override
    public ArrayList<ParticipantsInfo> setParticipantsInfo(long registeredId){
        List<Participants> participantsList = participantsDAO.selectParticipants(registeredId);
        ArrayList<ParticipantsInfo> participantsInfoArrayList = new ArrayList<>();
        for(Participants participant : participantsList){
            User participantUser = userDAO.selectOne(participant.getParticipantFK());
            ParticipantsInfo participantsInfo = new ParticipantsInfo(participantUser.getRelationship(),participantUser.getColor(),participantUser.getUid(),participantUser.getProfile(),participantUser.getName());
            participantsInfoArrayList.add(participantsInfo);
        }
        participantsInfoArrayList.sort((arg0,arg1)->{
            String name0 = arg0.getParticipantName();
            String name1 = arg1.getParticipantName();
            return userService.sortName(name0,name1);
        });
        return participantsInfoArrayList;
    }
    private ChallengeBarInfo setChallengeTitlePlusName(ChallengeBarInfo challengeBarInfo){
       String challengeTitle = challengeBarInfo.getChallengeTitle();
       String title = "";
       int count = 0;
       for(ParticipantsInfo participant : challengeBarInfo.getParticipantsInfo()){
           if (count < 2) {
               if(count > 0) title += ",";
               title += participant.getParticipantName();
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
