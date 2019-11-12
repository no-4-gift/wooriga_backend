package com.webapp.wooriga.mybatis.challenge.dao;

import com.webapp.wooriga.mybatis.vo.Certifications;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CertificationsDAO {
    void insertRegisteredDate(Certifications certifications);
    void updateCertification(Certifications certifications);
    List<Certifications> selectList(@Param("familyId") String familyId, @Param("firstdate") String firstdate, @Param("finaldate") String finaldate);
}
