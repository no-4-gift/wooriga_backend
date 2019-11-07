package com.webapp.wooriga.mybatis.challenge.dao;

import com.webapp.wooriga.mybatis.vo.Certifications;

public interface CertificationsDAO {
    void insertRegisteredDate(Certifications certifications);
    void updateCertification(Certifications certifications);
}
