package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.calendar.service.CalendarModuleService;
import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.result.*;
import com.webapp.wooriga.mybatis.exception.NoInformationException;
import com.webapp.wooriga.mybatis.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class MakeResultServiceImpl implements MakeResultService {
    private CertificationsDAO certificationsDAO;
    @Autowired
    public MakeResultServiceImpl(CertificationsDAO certificationsDAO){
        this.certificationsDAO = certificationsDAO;
    }
    @Override
    public EmptyDays makeEmptyDays(String familyId, long participantFK, Date registeredDate){
        return EmptyDays.builder()
                .familyId(familyId)
                .emptydate(registeredDate)
                .userIdFk(participantFK)
                .build();
    }
    @Override
    public ChallengeInfo makeChallengeInfo(ArrayList<Challenges> challengeList, ArrayList<UserInfo> userInfoArrayList){
        return ChallengeInfo.builder()
                .challenges(challengeList)
                .userInfo(userInfoArrayList)
                .build();
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
    @Override
    public Certifications makeCertifications(String date, int certificationTrue, long registeredIdFK, String certificationPhoto) throws RuntimeException{
        this.isThereAnyCertificationTrue(registeredIdFK,date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return Certifications.builder()
                    .certificationTrue(certificationTrue)
                    .registeredIdFK(registeredIdFK)
                    .registeredDate(new Date(simpleDateFormat.parse(date).getTime()))
                    .certificationPhoto(certificationPhoto)
                    .build();
        }catch(Exception e){
            throw new NoMatchPointException();
        }
    }
    @Override
    public ChallengeViewInfo makeChallengeViewInfo(ChallengeBarInfo challengeBarInfo,String challengeImage){
        return ChallengeViewInfo
                .builder()
                .challengeBarInfo(challengeBarInfo)
                .challengeImage(challengeImage)
                .build();
    }
    @Override
    public ChallengeBarInfo makeChallengeBarInfo(RegisteredChallenges registeredChallenges, User chief, Long registeredId, Challenges challenges, Certifications certifications,
                                                 ArrayList<UserInfo> userInfoArrayList,ArrayList<String> dateArrayList){
        return ChallengeBarInfo
                .builder()
                .challengeId(registeredChallenges.getChallengeIdFK())
                .userInfo(userInfoArrayList)
                .resolution(registeredChallenges.getResolution())
                .registeredId(registeredId)
                .challengeTitle(challenges.getTitle())
                .date(dateArrayList)
                .build();
    }
    @Override
    public CertificationInfo makeCertificationInfo(Certifications certifications){
        return CertificationInfo
                .builder()
                .cardDate(new SimpleDateFormat("MM.dd").format(certifications.getRegisteredDate()))
                .certificationDate(new SimpleDateFormat("yyyy.MM.dd").format(certifications.getRegisteredDate()))
                .certificationImage(certifications.getCertificationPhoto())
                .certificationTrue(certifications.getCertificationTrue())
                .build();
    }
    @Override
    public ChallengeDetailInfo makeChallengeDetailInfo(Challenges challenges,ArrayList<CertificationInfo> certificationInfoArrayList,RegisteredChallenges registeredChallenges){
      return ChallengeDetailInfo
              .builder()
              .certificationInfoArrayList(certificationInfoArrayList)
              .content(challenges.getContent())
              .resolution(registeredChallenges.getResolution())
              .summary(challenges.getSummary())
              .title(challenges.getTitle())
              .build();
    }
    @Override
    public UserInfo makeUserInfo(User user) throws RuntimeException{
        Optional.of(user).orElseThrow(NoInformationException::new);
        return UserInfo.builder()
                .color(user.getColor())
                .name(user.getUsername())
                .profile(user.getProfile())
                .relationship(user.getRelationship())
                .uid(user.getUid())
                .build();
    }

}
