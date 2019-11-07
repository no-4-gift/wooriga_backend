package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ParticipantsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.RegisteredChallengesDAO;
import com.webapp.wooriga.mybatis.vo.Participants;
import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;
import com.webapp.wooriga.mybatis.vo.Certifications;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class RegisteredChallengeService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private RegisteredChallengesDAO registeredChallengesDAO;
    private CertificationsDAO certificationsDAO;
    private ParticipantsDAO participantsDAO;

    @Autowired
    public RegisteredChallengeService(CertificationsDAO certificationsDAO, RegisteredChallengesDAO registeredChallengesDAO, ParticipantsDAO participantsDAO){
        this.participantsDAO = participantsDAO;
        this.registeredChallengesDAO = registeredChallengesDAO;
        this.certificationsDAO = certificationsDAO;
    }

    public void insertRegisteredChallenge(RegisteredChallenges registeredChallenges, Participants[] participants, Certifications[] certifications
            , HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(200);
        try {
            registeredChallengesDAO.insertRegisteredChallenge(registeredChallenges);
            long registeredId = registeredChallenges.getRegisteredId();
            if(participants.length > 6){
                //챌린지 최대 참여 인원수 6 넘음
                httpServletResponse.setStatus(402);
                return;
            }
            for (int i = 0; i < participants.length; i++) {
                participants[i].setRegisteredIdFK(registeredId);
                participantsDAO.insertParticipants(participants[i]);
            }
            if(certifications.length > 10) {
                //챌린지 신청 최대 일수 10 넘음
                httpServletResponse.setStatus(401);
                return;
            }
            for (int i = 0; i < certifications.length; i++) {
                certifications[i].setRegisteredIdFK(registeredId);
                certifications[i].setCertificationPhoto("https://woorigabucket.s3.ap-northeast-2.amazonaws.com/challenge/default.jpg");
                certificationsDAO.insertRegisteredDate(certifications[i]);
            }
        } catch (Exception e) {
            //insert fail
            httpServletResponse.setStatus(400);
            log.error(e.toString());
        }
    }
}
