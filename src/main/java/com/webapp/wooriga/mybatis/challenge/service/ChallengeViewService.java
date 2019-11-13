package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.result.ChallengeViewInfo;
import com.webapp.wooriga.mybatis.vo.Certifications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ChallengeViewService {
    ChallengeViewInfo sendChallengeViewInfo(Boolean ourTrue, Map<String,Object> info);
    HashMap<String,Integer> sendCertificationAndTotalNum(List<Certifications> certificationsList);
}
