package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.result.*;
import com.webapp.wooriga.mybatis.vo.*;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;

public interface MakeResultService {
    EmptyDays makeEmptyDays(String familyId, long participantFK, Date registeredDate);

    Certifications makeCertifications(String date, int certificationTrue, long registeredIdFK, String certificationPhoto) throws RuntimeException;

    ChallengeInfo makeChallengeInfo(ArrayList<Challenges> challengeList, ArrayList<UserInfo> userInfoArrayList);

    ChallengeViewInfo makeChallengeViewInfo(ChallengeBarInfo challengeBarInfo, String challengeImage);

    ChallengeBarInfo makeChallengeBarInfo(RegisteredChallenges registeredChallenges, User chief, Long registeredId, Challenges challenges, Certifications certifications,
                         ArrayList<UserInfo> userInfoArrayList, ArrayList<String> dateArrayList);

    CertificationInfo makeCertificationInfo(Certifications certifications);

    ChallengeDetailInfo makeChallengeDetailInfo(Challenges challenges,ArrayList<CertificationInfo> certificationInfoArrayList,RegisteredChallenges registeredChallenges);

    UserInfo makeUserInfo(User user) throws RuntimeException;
}
