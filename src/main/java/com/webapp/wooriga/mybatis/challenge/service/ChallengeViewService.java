package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeDetailInfo;

import java.util.ArrayList;

public interface ChallengeViewService {
    ArrayList<ChallengeBarInfo> sendChallengeViewInfo(Boolean ourTrue, String familyId, long uid);
    ChallengeDetailInfo sendChallengeDetailInfo(long uid, long registeredId) throws RuntimeException;
}
