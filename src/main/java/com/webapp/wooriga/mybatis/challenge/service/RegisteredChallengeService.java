package com.webapp.wooriga.mybatis.challenge.service;

import com.google.gson.JsonObject;
import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.vo.Participants;
import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface RegisteredChallengeService {
    void insertRegisteredChallenge(RegisteredChallenges registeredChallenges, Participants[] participants, Certifications[] certifications) throws RuntimeException;
    String conveyResolution(Map<String,Object> info) throws RuntimeException;
}
