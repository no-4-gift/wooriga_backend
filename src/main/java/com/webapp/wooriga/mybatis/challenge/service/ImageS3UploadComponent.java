package com.webapp.wooriga.mybatis.challenge.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.webapp.wooriga.mybatis.SuperS3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Component
public class ImageS3UploadComponent extends SuperS3Uploader {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public ImageS3UploadComponent(AmazonS3Client amazonS3Client) {
        super(amazonS3Client);
    }

    public String upload(MultipartFile multipartFile, String userName, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        if(multipartFile != null) {
            File uploadFile = super.convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
            return upload(uploadFile, userName, response);
        }
        else return "https://woorigabucket.s3.ap-northeast-2.amazonaws.com/challenge/default.jpg";
    }

    private String upload(File uploadFile, String userName, HttpServletResponse response) {
        if(userName == null) return null;
        String fileName = "challenge/" + userName;
        String uploadImageUrl = super.putS3(uploadFile, fileName);
        super.removeNewFile(uploadFile);
        if(uploadImageUrl != null)
            response.setStatus(200);
        return uploadImageUrl;
    }



}

