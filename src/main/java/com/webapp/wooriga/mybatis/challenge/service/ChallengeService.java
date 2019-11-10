package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.result.ChallengeInfo;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.ArrayList;

public interface ChallengeService {
    void certificateChallenge(long registeredId, String date, MultipartFile file) throws RuntimeException;
    ChallengeInfo sendChallengeInfo(ArrayList<String> date, String familyId);
}
