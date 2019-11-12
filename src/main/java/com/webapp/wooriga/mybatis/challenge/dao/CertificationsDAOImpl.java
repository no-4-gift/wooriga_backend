package com.webapp.wooriga.mybatis.challenge.dao;

import com.webapp.wooriga.mybatis.challenge.mapper.CertificationsMapper;
import com.webapp.wooriga.mybatis.vo.Certifications;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertificationsDAOImpl implements CertificationsDAO {

    @Autowired
    private CertificationsMapper certificationsMapper;

    @Override
    public void insertRegisteredDate(Certifications certifications){
        certificationsMapper.insertRegisteredDates(certifications);
    }
    @Override
    public void updateCertification(Certifications certifications){
        certificationsMapper.updateCertification(certifications);
    }
    @Override
    public List<Certifications> selectList(@Param("familyId") String familyId, @Param("firstdate") String firstdate, @Param("finaldate") String finaldate){
        return certificationsMapper.selectList(familyId, firstdate, finaldate);
    }
}
