package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.result.CertificationInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.exception.NoInformationException;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.vo.Participants;
import com.webapp.wooriga.mybatis.vo.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MakeArrayListServiceImpl implements MakeArrayListService {
    private CertificationsDAO certificationsDAO;
    private MakeResultService makeResultService;
    private UserDAO userDAO;
    public MakeArrayListServiceImpl(UserDAO userDAO,CertificationsDAO certificationsDAO,MakeResultService makeResultService){
        this.certificationsDAO =certificationsDAO;
        this.makeResultService = makeResultService;
        this.userDAO = userDAO;
    }
    @Override
    public ArrayList<Long> makeParticipantsList(Participants[] participants){
        ArrayList<Long> participantsList = new ArrayList<>();
        for(int i = 0; i < participants.length; i++)
            participantsList.add(participants[i].getParticipantFK());
        return participantsList;
    }

    @Override
    public List<Certifications> makeCertificationsList(long registeredId) throws RuntimeException{
        List<Certifications> certificationsList =  certificationsDAO.selectChallengeDetailInfo(registeredId);
        if(certificationsList.isEmpty()) throw new NoMatchPointException();
        return certificationsList;
    }
    @Override
    public ArrayList<CertificationInfo> makeCertificationInfoArrayList(List<Certifications> certificationsList){
        ArrayList<CertificationInfo> certificationInfoArrayList = new ArrayList<>();
        for(Certifications certifications : certificationsList)
            certificationInfoArrayList.add(makeResultService.makeCertificationInfo(certifications));
        return certificationInfoArrayList;
    }
    @Override
    public ArrayList<UserInfo> makeUserInfoArrayList(HashMap<Long,Integer> userMap,int dateSize){
        ArrayList<UserInfo> userInfoList = new ArrayList<>();
        Iterator<Long> iterator = userMap.keySet().iterator();
        while(iterator.hasNext()) {
            Long uid = iterator.next();
            if (dateSize == userMap.get(uid))
                userInfoList = this.convertUserToUserInfoAndAddArrayList(Optional.of(userDAO.selectOne(uid))
                        .orElseThrow(NoInformationException::new),userInfoList);
        }
        return userInfoList;
    }
    @Override
    public ArrayList<UserInfo> userInfoToArrayList(User chief, List<User> userInfoList) throws RuntimeException{
        if(userInfoList.size() == 0) throw new NoInformationException();
        ArrayList<UserInfo> userInfoArrayList = convertUserToUserInfoAndAddArrayList(chief,new ArrayList<UserInfo>());
        return userInfoArrayList;
    }
    @Override
    public ArrayList<UserInfo> addParticipantsToArrayList(List<User> userInfoList,User chief){
        ArrayList<UserInfo> participantInfoArrayList = new ArrayList<>();
        for(User user : userInfoList) {
            if(user.getUid() == chief.getUid()) continue;
            participantInfoArrayList = convertUserToUserInfoAndAddArrayList(user,participantInfoArrayList);
        }
        return participantInfoArrayList;
    }
    @Override
    public ArrayList<UserInfo> convertUserToUserInfoAndAddArrayList(User user,ArrayList<UserInfo> userInfoArrayList){
        userInfoArrayList.add(makeResultService.makeUserInfo(user));
        return userInfoArrayList;
    }


}
