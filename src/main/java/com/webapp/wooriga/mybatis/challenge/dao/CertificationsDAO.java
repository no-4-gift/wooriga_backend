package com.webapp.wooriga.mybatis.challenge.dao;

import com.webapp.wooriga.mybatis.vo.Certifications;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CertificationsDAO {
    void insertRegisteredDate(Certifications certifications);
    void updateCertification(Certifications certifications);
    List<Certifications> selectList(@Param("familyId") String familyId, @Param("firstdate") String firstdate, @Param("finaldate") String finaldate);
    List<Certifications> selectMyChallengeViewInfo(HashMap<String,Object> infoHashMap);
    List<Certifications> selectOurChallengeViewInfo(HashMap<String,Object> infoHashMap);
    List<Certifications> selectChallengeDetailInfo(@Param("registeredId") long registeredId);
}
