package com.webapp.wooriga.mybatis.challenge.mapper;

import com.webapp.wooriga.mybatis.vo.Certifications;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CertificationsMapper {
    void insertRegisteredDates(Certifications certifications);
    void updateCertification(Certifications certifications);
}
