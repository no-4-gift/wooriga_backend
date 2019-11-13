package com.webapp.wooriga.mybatis.challenge.service;

public interface ChallengeViewService {
    ChallengeViewInfo sendMyChallengeViewInfo(String familyId, Long uid);
    ChallengeViewInfo sendOurChallengeViewInfo(String familyId);

}
