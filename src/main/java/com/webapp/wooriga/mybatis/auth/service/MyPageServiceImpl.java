package com.webapp.wooriga.mybatis.auth.service;

import com.webapp.wooriga.mybatis.auth.dao.CodeUserDAO;
import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.auth.result.MyAchievement;
import com.webapp.wooriga.mybatis.auth.result.MyRecordInfo;
import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeViewInfo;
import com.webapp.wooriga.mybatis.challenge.service.ChallengeViewService;
import com.webapp.wooriga.mybatis.exception.NoInformationException;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.vo.CodeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MyPageServiceImpl implements MyPageService {
    private ChallengeViewService challengeViewService;
    private CertificationsDAO certificationsDAO;
    private CodeUserDAO codeUserDAO;
    @Autowired
    public MyPageServiceImpl(CodeUserDAO codeUserDAO,CertificationsDAO certificationsDAO,ChallengeViewService challengeViewService){
        this.challengeViewService = challengeViewService;
        this.certificationsDAO = certificationsDAO;
        this.codeUserDAO = codeUserDAO;
    }
    public MyPageServiceImpl(){ }

    @Override
    public MyRecordInfo sendMyRecordInfo(String familyId,long uid) throws RuntimeException{
        HashMap<String,Object> userInfoMap = new HashMap<>();
        userInfoMap.put("familyId",familyId);
        userInfoMap.put("uid",uid);
        List<Certifications> certificationsList = certificationsDAO.selectMyInfo(userInfoMap);
        if(certificationsList.isEmpty()) throw new NoMatchPointException();
        ArrayList<ChallengeViewInfo> challengeViewInfoArrayList = challengeViewService.makeChallengeViewInfo(certificationsList,3);
        ArrayList<MyAchievement> myAchievementArrayList = this.setSuccessTrueOnMyAchievement(challengeViewInfoArrayList);
        MyRecordInfo myRecordInfo = this.setAchievementOnMyRecord(myAchievementArrayList,userInfoMap);
        return myRecordInfo;
    }
    private ArrayList<MyAchievement> setSuccessTrueOnMyAchievement(ArrayList<ChallengeViewInfo> challengeViewInfoArrayList){
        ArrayList<MyAchievement> myAchievementArrayList = new ArrayList<>();
        for(ChallengeViewInfo challengeViewInfo : challengeViewInfoArrayList){
            Boolean successTrue;
            if(challengeViewInfo.getChallengeBarInfo().getPercentage() == 100) successTrue = true;
            else successTrue = false;
            MyAchievement myAchievement = new MyAchievement(successTrue,challengeViewInfo);
            myAchievementArrayList.add(myAchievement);
        }
        return myAchievementArrayList;
    }
    private MyRecordInfo setAchievementOnMyRecord(ArrayList<MyAchievement> myAchievementArrayList,HashMap<String,Object> userInfoMap){
       int successNum = 0,failNum = 0,totalNum = 0;
       for(MyAchievement myAchievement : myAchievementArrayList){
           totalNum++;
           if(myAchievement.getSuccessTrue()) successNum++;
           else failNum++;
       }
       int presentNum = certificationsDAO.selectPresentNum(userInfoMap);
        MyRecordInfo myRecordInfo = new MyRecordInfo(presentNum,myAchievementArrayList,successNum,failNum,(int)(successNum/(double)totalNum*100),totalNum);
       return myRecordInfo;
    }
    @Override
    public void delegateChief(String familyId, long uid, long chiefId) throws RuntimeException{
        int trueUserNum = codeUserDAO.checkUser(uid);
        Long trueFamily = codeUserDAO.getUidFromCode(familyId);
        if(trueUserNum == 0 || trueFamily == null) throw new NoInformationException();
        CodeUser codeUser = new CodeUser(chiefId,familyId);
        try {
            codeUserDAO.updateChief(codeUser);
        }catch(Exception e){
            throw new NoInformationException();
        }
    }
}
