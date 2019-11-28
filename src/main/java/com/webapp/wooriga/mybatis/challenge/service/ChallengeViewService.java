package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeDetailInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeViewInfo;
import com.webapp.wooriga.mybatis.vo.Certifications;

import java.util.ArrayList;
import java.util.List;

public interface ChallengeViewService {
    ArrayList<ChallengeViewInfo> sendChallengeViewInfo(Boolean ourTrue, String familyId, long uid);
    ChallengeDetailInfo sendChallengeDetailInfo(long uid, long registeredId) throws RuntimeException;
    ArrayList<ChallengeViewInfo> makeChallengeViewInfo(List<Certifications> certificationsList, int pageId);
}
