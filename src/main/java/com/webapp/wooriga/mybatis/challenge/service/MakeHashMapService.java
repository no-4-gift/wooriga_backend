package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.vo.Participants;

import java.util.HashMap;

public interface MakeHashMapService {
    HashMap<String,Object> makeFamilyHashMap(long chiefId, Participants[] participants, String familyId);
    HashMap<String,Object> makeChallengeHashMap(ChallengeBarInfo challengeBarInfo);
    HashMap<Long,Integer> makeUserHashMap(HashMap<String,Object> emptyMap);
}
