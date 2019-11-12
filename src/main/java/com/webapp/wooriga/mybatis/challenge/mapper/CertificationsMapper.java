package com.webapp.wooriga.mybatis.challenge.mapper;

import com.webapp.wooriga.mybatis.vo.Certifications;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CertificationsMapper {
    void insertRegisteredDates(Certifications certifications);
    void updateCertification(Certifications certifications);
    List<Certifications> selectList(@Param("familyId")String familyId,@Param("firstdate") String firstdate,@Param("finaldate") String finaldate);
}
