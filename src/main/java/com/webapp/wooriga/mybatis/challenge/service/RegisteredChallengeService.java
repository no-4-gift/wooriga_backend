package com.webapp.wooriga.mybatis.challenge.service;

import com.google.gson.JsonObject;
import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.vo.Participants;
import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.Map;

public interface RegisteredChallengeService {
    void insertRegisteredChallenge(RegisteredChallenges registeredChallenges, Participants[] participants, Certifications[] certifications) throws RuntimeException;
    void cancelChallengeCertification(Map<String,Object> infoMap) throws RuntimeException;
    void validateRegisteredChallenge(RegisteredChallenges registeredChallenges, Participants[] participants, Certifications[] certifications) throws RuntimeException;
    void registerChallenge(RegisteredChallenges registeredChallenges, Participants[] participants, Certifications[] certifications) throws RuntimeException;
    void initializeCertificationsForChallenge(Certifications[] certifications,long registeredId) throws RuntimeException;
    void initializeParticipantsForChallenge(Participants[] participants, long registeredId) throws RuntimeException;
    void initializeChallengeCertification(Long registeredId, Date registeredDate) throws RuntimeException;
}
