package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.vo.Certifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ChallengeViewServiceImpl implements ChallengeViewService {
    private ChallengeModuleService challengeModuleService;
    private CertificationsDAO certificationsDAO;
    public ChallengeViewServiceImpl(){}

    @Autowired
    public ChallengeViewServiceImpl(CertificationsDAO certificationsDAO,ChallengeModuleService challengeModuleService){
        this.challengeModuleService = challengeModuleService;
        this.certificationsDAO = certificationsDAO;
    }
    @Override
    public ChallengeViewInfo sendMyChallengeViewInfo(String familyId, Long uid) throws RuntimeException{
        HashMap<String,Object> infoHashMap = new HashMap<>();
        infoHashMap.put("familyId", familyId);
        infoHashMap.put("uid",uid);
        List<Certifications> certificationsList = certificationsDAO.selectMyChallengeViewInfo(infoHashMap);
        if(certificationsList.size() > 0) {
            challengeModuleService.sendChallengeViewInfo(certificationsList);
        }
    }
    @Override
    public ChallengeViewInfo sendOurChallengeViewInfo(String familyId){

    }
}
