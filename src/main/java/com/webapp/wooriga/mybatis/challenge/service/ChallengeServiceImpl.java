package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.exception.NoStoringException;
import com.webapp.wooriga.mybatis.exception.WrongCodeException;
import com.webapp.wooriga.mybatis.vo.Certifications;

import com.webapp.wooriga.mybatis.vo.Challenges;
import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;
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
import java.util.Optional;


@Transactional
@Service
public class ChallengeServiceImpl implements ChallengeService{
    Logger log = LoggerFactory.getLogger(this.getClass());
    private ChallengesDAO challengesDAO;
    private CertificationsDAO certificationsDAO;
    private ImageS3UploadComponent imageS3UploadComponent;
    private ChallengeModuleService challengeModuleService;
    private MakeResultService makeResultService;
    @Autowired
    public ChallengeServiceImpl(ChallengeModuleService challengeModuleService,ChallengesDAO challengesDAO, ImageS3UploadComponent imageS3UploadComponent
    , CertificationsDAO certificationsDAO,MakeResultService makeResultService){

        this.challengesDAO = challengesDAO;
        this.certificationsDAO = certificationsDAO;
        this.imageS3UploadComponent = imageS3UploadComponent;
        this.challengeModuleService = challengeModuleService;
        this.makeResultService = makeResultService;
    }
    public ChallengeServiceImpl(){}

    @Override
    public void certificateChallenge(long registeredId, String date, MultipartFile file) throws RuntimeException{
        this.updateCertification(
                makeResultService.makeCertifications(date,1,registeredId,
                        uploadImgForCertification(date,registeredId,file)));
    }
    @Override
    public ChallengeInfo sendChallengeInfo(ArrayList<String> date, String familyId) throws RuntimeException{
        if(date.size() > 10) throw new NoMatchPointException();
        ArrayList<UserInfo> userInfoList= challengeModuleService.findUserSetToEmpty(date.size(),makeEmptyMap(familyId,date));
        if(userInfoList.isEmpty()) throw new NoMatchPointException();
        return makeResultService.makeChallengeInfo(challengesDAO.selectChallengeList(),userInfoList);
    }

    private HashMap<String,Object> makeEmptyMap(String familyId,ArrayList<String> date){
        HashMap<String,Object> emptyMap = new HashMap<>();
        emptyMap.put("familyId",familyId);
        emptyMap.put("date",date);
        return emptyMap;
    }
    @Override
    public String uploadImgForCertification(String date,long registeredId,MultipartFile file) throws RuntimeException{
        try{
            return Optional.of(imageS3UploadComponent.upload(file, "challenge"+Long.toString(registeredId) + date)).orElseThrow(NoStoringException::new);
        }
        catch(Exception e){
            throw new WrongCodeException();
        }
    }
    @Override
    @Transactional
    public void updateCertification(Certifications certifications) throws RuntimeException{
        try {
            certificationsDAO.updateCertification(certifications);
        }
        catch(Exception e){
            throw new NoStoringException();
        }
    }

}

