package com.webapp.wooriga.mybatis.challenge.service;

import com.webapp.wooriga.mybatis.challenge.dao.CertificationsDAO;
import com.webapp.wooriga.mybatis.challenge.dao.ChallengesDAO;
import com.webapp.wooriga.mybatis.vo.Certifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

@Service
public class ChallengeService {
    private ChallengesDAO challengesDAO;
    private CertificationsDAO certificationsDAO;
    private ImageS3UploadComponent imageS3UploadComponent;
    @Autowired
    public ChallengeService(ChallengesDAO challengesDAO,ImageS3UploadComponent imageS3UploadComponent
    ,CertificationsDAO certificationsDAO){
        this.challengesDAO = challengesDAO;
        this.certificationsDAO = certificationsDAO;
        this.imageS3UploadComponent = imageS3UploadComponent;
    }
    public ChallengeService(){}

    public void certificateChallenge(long registeredId, Date date, MultipartFile file, HttpServletResponse response){
        Certifications certifications = new Certifications(registeredId,date);
        try {
            String url = imageS3UploadComponent.upload(file, Long.toString(registeredId) + date.toString(), response);
            //image가 s3에 제대로 저장되지 않았음.
            if(url != null) {response.setStatus(401); return;}
            else certifications.setCertificationPhoto(url);
        }
        catch(Exception e){
            //multipartfile 에서 file로 바꾸는데 실패함.
            response.setStatus(400);
            return;
        }
        try {
            certificationsDAO.updateCertification(certifications);
            response.setStatus(200);
        }
        catch(Exception e){
            //update 실패함.
            response.setStatus(402);
        }
    }
}
