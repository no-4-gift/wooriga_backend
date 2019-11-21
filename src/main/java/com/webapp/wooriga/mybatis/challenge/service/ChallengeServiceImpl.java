package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.exception.NoStoringException;
import com.webapp.wooriga.mybatis.exception.WrongCodeException;
import com.webapp.wooriga.mybatis.vo.Certifications;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Transactional
@Service
public class ChallengeServiceImpl implements ChallengeService{
    Logger log = LoggerFactory.getLogger(this.getClass());
    private ChallengesDAO challengesDAO;
    private CertificationsDAO certificationsDAO;
    private ImageS3UploadComponent imageS3UploadComponent;
    private ChallengeModuleService challengeModuleService;

    @Autowired
    public ChallengeServiceImpl(ChallengeModuleService challengeModuleService,ChallengesDAO challengesDAO, ImageS3UploadComponent imageS3UploadComponent
    , CertificationsDAO certificationsDAO){

        this.challengesDAO = challengesDAO;
        this.certificationsDAO = certificationsDAO;
        this.imageS3UploadComponent = imageS3UploadComponent;
        this.challengeModuleService = challengeModuleService;
    }
    public ChallengeServiceImpl(){}

    @Override
    public void certificateChallenge(long registeredId, String date, MultipartFile file) throws RuntimeException{
        Certifications certifications;
        String url;
        try {
            isThereAnyCertificationTrue(registeredId,date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            certifications = new Certifications(registeredId, new Date(simpleDateFormat.parse(date).getTime()),
                    1);
        }
        catch(Exception e){
            throw new NoMatchPointException();
        }
        try{
            url = imageS3UploadComponent.upload(file, "challenge"+Long.toString(registeredId) + date);
        }
        catch(Exception e){
            throw new WrongCodeException();
        }

        try {
            if(url == null) throw new NoStoringException();
            else certifications.setCertificationPhoto(url);
            certificationsDAO.updateCertification(certifications);
        }
        catch(Exception e){
            throw new NoStoringException();
        }
    }
    @Override
    public ChallengeInfo sendChallengeInfo(ArrayList<String> date, String familyId) throws RuntimeException{
        ChallengeInfo challengeInfo = new ChallengeInfo();
        try {
            challengeInfo.setChallenges(challengesDAO.selectChallengeList());
            if(date.size() > 10) throw new NoMatchPointException();
            HashMap<String,Object> emptyMap = new HashMap<>();
            emptyMap.put("familyId",familyId);
            emptyMap.put("date",date);
            ArrayList<UserInfo> userInfoList= challengeModuleService.findUserSetToEmpty(date.size(),emptyMap);
            if(userInfoList.isEmpty()) throw new NoMatchPointException();
            challengeInfo.setUserInfo(userInfoList);
        }catch(RuntimeException e){
            log.error(e.toString());
            throw new NoMatchPointException();
        }
        return challengeInfo;
    }
    private void isThereAnyCertificationTrue(long registeredId,String date){
        HashMap<String,Object> infoHashMap = new HashMap<>();
        ArrayList<String> dateList = new ArrayList<>();
        dateList.add(date);
        infoHashMap.put("registeredId",registeredId);
        infoHashMap.put("dateList",dateList);
        List<Certifications> certificationsList= certificationsDAO.selectNonCertificateDate(infoHashMap);
        if(certificationsList.isEmpty()) throw new NoMatchPointException();
    }

}

