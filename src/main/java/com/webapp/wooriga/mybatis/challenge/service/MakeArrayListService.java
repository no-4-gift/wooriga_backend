package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.result.CertificationInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.vo.Participants;
import com.webapp.wooriga.mybatis.vo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface MakeArrayListService {
    ArrayList<Long> makeParticipantsList(Participants[] participants);

    List<Certifications> makeCertificationsList(long registeredId);

    ArrayList<CertificationInfo> makeCertificationInfoArrayList(List<Certifications> certificationsList);

    ArrayList<UserInfo> convertUserToUserInfoAndAddArrayList(User user, ArrayList<UserInfo> userInfoArrayList);

    ArrayList<UserInfo> addParticipantsToArrayList(List<User> userInfoList, User chief);

    ArrayList<UserInfo> userInfoToArrayList(User chief, List<User> userInfoList) throws RuntimeException;

    ArrayList<UserInfo> makeUserInfoArrayList(HashMap<Long, Integer> userMap, int dateSize);

    ;
}
