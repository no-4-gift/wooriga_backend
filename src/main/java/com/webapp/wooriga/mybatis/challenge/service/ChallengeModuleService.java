package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import com.webapp.wooriga.mybatis.vo.Participants;
import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ChallengeModuleService {
    Boolean ifCorrectUserIntheFamily(long chiefId, Participants[] participants,String familyId);
    void validateParticipantsNum(int participantsNum);
    void validateChallengeDateNum(int dateNum);
    void ifCorrectParticipants(Certifications[] certifications, RegisteredChallenges registeredChallenges,
                                  Participants[] participants);
    ArrayList<UserInfo> findUserSetToEmpty(int dateSize, HashMap<String,Object> emptyMap);

}
