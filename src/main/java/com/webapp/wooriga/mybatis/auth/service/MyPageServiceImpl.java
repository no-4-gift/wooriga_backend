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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
        HashMap<String,Object> userInfoMap = this.makeUserInfoHashMap(familyId,uid);
        List<Certifications> certificationsList = certificationsDAO.selectMyInfo(userInfoMap);
        if(certificationsList.isEmpty()) throw new NoMatchPointException();
        ArrayList<MyAchievement> myAchievementArrayList = this.setSuccessTrueOnMyAchievement(challengeViewService.makeChallengeViewInfo(certificationsList,3));
        return this.setAchievementOnMyRecord(myAchievementArrayList,userInfoMap);
    }
    private ArrayList<MyAchievement> setSuccessTrueOnMyAchievement(ArrayList<ChallengeViewInfo> challengeViewInfoArrayList){
        ArrayList<MyAchievement> myAchievementArrayList = new ArrayList<>();
        for(ChallengeViewInfo challengeViewInfo : challengeViewInfoArrayList){
            Boolean successTrue = challengeViewInfo.getChallengeBarInfo().getPercentage() == 100;
            myAchievementArrayList = makeMyAchievementArrayList(myAchievementArrayList,successTrue,challengeViewInfo);
        }
        return myAchievementArrayList;
    }
    private MyRecordInfo setAchievementOnMyRecord(ArrayList<MyAchievement> myAchievementArrayList,HashMap<String,Object> userInfoMap){
       int successNum = 0,failNum = 0,totalNum = myAchievementArrayList.size();
       for(MyAchievement myAchievement : myAchievementArrayList){
           if(myAchievement.getSuccessTrue()) successNum++;
           else failNum++;
       }
       return new MyRecordInfo(certificationsDAO.selectPresentNum(userInfoMap),
               myAchievementArrayList,successNum,failNum,(int)(successNum/(double)totalNum*100),totalNum);
    }
    @Transactional
    @Override
    public void delegateChief(String familyId, long uid, long chiefId) throws RuntimeException{
        Optional.of(codeUserDAO.getUidFromCode(familyId)).orElseThrow(NoInformationException::new);
        if(codeUserDAO.checkTrueUserNum(uid) == 0) throw new NoInformationException();
        this.updateChief(this.makeCodeUser(chiefId,familyId));
    }
    @Override
    public CodeUser makeCodeUser(long chiefId,String familyId){
        return CodeUser.
                builder()
                .uid(chiefId)
                .code(familyId)
                .build();
    }
    @Transactional
    @Override
    public void updateChief(CodeUser codeUser) throws RuntimeException{
        try {
            codeUserDAO.updateChief(codeUser);
        }catch(Exception e){
            throw new NoInformationException();
        }
    }
    private ArrayList<MyAchievement> makeMyAchievementArrayList(ArrayList<MyAchievement> myAchievementArrayList,Boolean successTrue,ChallengeViewInfo challengeViewInfo){
        MyAchievement myAchievement = new MyAchievement(successTrue,challengeViewInfo);
        myAchievementArrayList.add(myAchievement);
        return myAchievementArrayList;
    }
    private HashMap<String,Object> makeUserInfoHashMap(String familyId,long uid) {
        HashMap<String,Object> userInfoMap = new HashMap<>();
        userInfoMap.put("familyId",familyId);
        userInfoMap.put("uid",uid);
        return userInfoMap;
    }
}