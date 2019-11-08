package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.challenge.exception.NoMatchPointException;
import com.webapp.wooriga.mybatis.challenge.exception.NoStoringException;
import com.webapp.wooriga.mybatis.challenge.exception.WrongCodeException;
import com.webapp.wooriga.mybatis.vo.Certifications;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Transactional
@Service
public class ChallengeServiceImpl implements ChallengeService{
    Logger log = LoggerFactory.getLogger(this.getClass());
    private ChallengesDAO challengesDAO;
    private CertificationsDAO certificationsDAO;
    private ImageS3UploadComponent imageS3UploadComponent;
    @Autowired
    public ChallengeServiceImpl(ChallengesDAO challengesDAO, ImageS3UploadComponent imageS3UploadComponent
    , CertificationsDAO certificationsDAO){
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
        }
        catch(Exception e){
            throw new NoMatchPointException();
        }
        try {
            String url = imageS3UploadComponent.upload(file, "challenge"+Long.toString(registeredId) + date);
            log.info(url);
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
            throw new NoStoringException();
        }
    }
}
