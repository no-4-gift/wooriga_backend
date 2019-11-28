package com.webapp.wooriga.mybatis.auth.service;

import com.webapp.wooriga.mybatis.auth.result.MyAchievement;
import com.webapp.wooriga.mybatis.auth.result.MyRecordInfo;
import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeViewInfo;
import com.webapp.wooriga.mybatis.challenge.service.ChallengeViewService;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.vo.Certifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MyPageServiceImpl implements MyPageService {
    private ChallengeViewService challengeViewService;
    private CertificationsDAO certificationsDAO;
    @Autowired
    public MyPageServiceImpl(CertificationsDAO certificationsDAO,ChallengeViewService challengeViewService){
        this.challengeViewService = challengeViewService;
        this.certificationsDAO = certificationsDAO;
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
        MyRecordInfo myRecordInfo = this.setAchievementOnMyRecord(myAchievementArrayList);
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
    private MyRecordInfo setAchievementOnMyRecord(ArrayList<MyAchievement> myAchievementArrayList){
       int successNum = 0,failNum = 0,totalNum = 0;
       for(MyAchievement myAchievement : myAchievementArrayList){
           totalNum++;
           if(myAchievement.getSuccessTrue()) successNum++;
           else failNum++;
       }
        MyRecordInfo myRecordInfo = new MyRecordInfo(myAchievementArrayList,successNum,failNum,(int)(successNum/(double)totalNum*100),totalNum);
       return myRecordInfo;
    }
}
