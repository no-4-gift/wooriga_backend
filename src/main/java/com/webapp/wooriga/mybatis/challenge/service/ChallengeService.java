package com.webapp.wooriga.mybatis.challenge.service;

import org.springframework.web.multipart.MultipartFile;

public interface ChallengeService {
    void certificateChallenge(long registeredId, String date, MultipartFile file) throws RuntimeException;
}
