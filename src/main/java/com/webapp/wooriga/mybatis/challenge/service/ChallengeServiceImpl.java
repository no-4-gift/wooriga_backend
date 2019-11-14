package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.calendar.dao.EmptyDaysDAO;
import com.webapp.wooriga.mybatis.calendar.dao.EmptyDaysDAOImpl;
import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.exception.NoStoringException;
import com.webapp.wooriga.mybatis.exception.WrongCodeException;
import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.vo.Challenges;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import com.webapp.wooriga.mybatis.vo.User;
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
import java.util.Iterator;
import java.util.List;

@Transactional
@Service
public class ChallengeServiceImpl implements ChallengeService{
    Logger log = LoggerFactory.getLogger(this.getClass());
    private ChallengesDAO challengesDAO;
    private EmptyDaysDAO emptyDaysDAO;
    private CertificationsDAO certificationsDAO;
    private ImageS3UploadComponent imageS3UploadComponent;
    private UserDAO userDAO;
    @Autowired
    public ChallengeServiceImpl(UserDAO userDAO,EmptyDaysDAO emptyDaysDAO,ChallengesDAO challengesDAO, ImageS3UploadComponent imageS3UploadComponent
    , CertificationsDAO certificationsDAO){
        this.userDAO = userDAO;
        this.emptyDaysDAO = emptyDaysDAO;
        this.challengesDAO = challengesDAO;
        this.certificationsDAO = certificationsDAO;
        this.imageS3UploadComponent = imageS3UploadComponent;
    }
    public ChallengeServiceImpl(){}

    public void certificateChallenge(long registeredId, String date, MultipartFile file) throws RuntimeException{
        Certifications certifications = new Certifications();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date registeredDate = new Date( simpleDateFormat.parse(date).getTime());
            certifications.setRegisteredDate(registeredDate);
            certifications.setRegisteredIdFK(registeredId);
            certifications.setCertificationTrue(1);
        }
        catch(Exception e){
            throw new NoMatchPointException();
        }
        try {
            String url = imageS3UploadComponent.upload(file, "challenge"+Long.toString(registeredId) + date);
            if(url == null) throw new NoStoringException();
            else certifications.setCertificationPhoto(url);
        }
        catch(Exception e){
            throw new WrongCodeException();
        }
        try {
            certificationsDAO.updateCertification(certifications);
        }
        catch(Exception e){
            log.error(e.toString());
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
            HashMap<Long,Integer> userMap = new HashMap<>();
            List<EmptyDays> emptyDays = emptyDaysDAO.selectToDate(emptyMap);
            for(EmptyDays emptyDay : emptyDays){
                long id = emptyDay.getUserIdFk();
                if(!userMap.isEmpty() && userMap.containsKey(id)){
                   int num = userMap.get(id);
                    userMap.replace(id,num+1);
                }
                else
                    userMap.put(id,1);
            }
            int dateSize = date.size();
            Iterator<Long> iterator = userMap.keySet().iterator();
            ArrayList<UserInfo> userInfoList = new ArrayList<>();
           while(iterator.hasNext()) {
               Long key = iterator.next();
               if (dateSize == userMap.get(key)) {
                   User user = userDAO.selectOne(key);
                   UserInfo userInfo = new UserInfo(user.getName(),user.getProfile(),user.getColor(),user.getUid());
                   userInfoList.add(userInfo);
               }
           }
           challengeInfo.setUserInfo(userInfoList);
        }catch(RuntimeException e){
            log.error(e.toString());
            throw new NoMatchPointException();
        }
        return challengeInfo;
    }
}
