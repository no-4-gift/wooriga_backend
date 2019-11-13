package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.calendar.service.CalendarModuleService;
import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeViewInfo;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.vo.Certifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChallengeViewServiceImpl implements ChallengeViewService {
    private CalendarModuleService calendarModuleService;
    private CertificationsDAO certificationsDAO;
    public ChallengeViewServiceImpl(){}

    @Autowired
    public ChallengeViewServiceImpl(CertificationsDAO certificationsDAO,CalendarModuleService calendarModuleService){
        this.calendarModuleService = calendarModuleService;
        this.certificationsDAO = certificationsDAO;
    }
    @Override
    public ChallengeViewInfo sendChallengeViewInfo(Boolean ourTrue, Map<String,Object> info) throws RuntimeException{
        HashMap<String,Object> infoHashMap = new HashMap<>();
        ChallengeViewInfo challengeViewInfo = new ChallengeViewInfo();
        infoHashMap.put("familyId", info.get("familyId"));
        infoHashMap.put("uid",info.get("uid"));
        List<Certifications> certificationsList;
        if(!ourTrue) {
            certificationsList = certificationsDAO.selectMyChallengeViewInfo(infoHashMap);
        }
        else {
            certificationsList = certificationsDAO.selectOurChallengeViewInfo(infoHashMap);
        }
        if(certificationsList.size() > 0) {
            challengeViewInfo.setChallengeBarInfoArrayList(calendarModuleService.setChallengeBarInfoList(true, certificationsList));
            HashMap<String,Integer> numHashMap = this.sendCertificationAndTotalNum(certificationsList);
            challengeViewInfo.setTotalNum(numHashMap.get("totalNum"));
            challengeViewInfo.setCertificationNum(numHashMap.get("certificationNum"));
            return challengeViewInfo;
        }
        else
            throw new NoMatchPointException();
    }
    @Override
    public HashMap<String,Integer> sendCertificationAndTotalNum(List<Certifications> certificationsList){
        HashMap<String,Integer> numHashMap = new HashMap<>();
        numHashMap.put("totalNum",certificationsList.size());
        int i = 0;
        for(Certifications certifications : certificationsList){
            if(certifications.getCertificationTrue() == 1)
                i++;
        }
        numHashMap.put("certificationNum",i);
        return numHashMap;
    }

}
