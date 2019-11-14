package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.vo.Certifications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ChallengeViewService {
    ArrayList<ChallengeBarInfo> sendChallengeViewInfo(Boolean ourTrue, String familyId, long uid);
}
