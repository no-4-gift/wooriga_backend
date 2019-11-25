package com.webapp.wooriga.mybatis.challenge.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
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

    @Value("${cloud.aws.bucket}")
    private String bucket;

    public ImageS3UploadComponent(AmazonS3Client amazonS3Client) {
        super(amazonS3Client);
    }

    public String upload(MultipartFile multipartFile, String userName) throws IOException {
        if(multipartFile != null) {
            File uploadFile = super.convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
            return upload(uploadFile, userName);
        }
        else return "https://woorigabucket.s3.ap-northeast-2.amazonaws.com/challenge/default.jpg";
    }

    private String upload(File uploadFile, String userName) {
        if(userName == null) return null;
        String fileName = "challenge/" + userName;
        log.error(fileName);
        String uploadImageUrl = super.putS3(uploadFile, fileName);
        super.removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // 파일 삭제
    public void fileDelete(String userName) {
        String fileName = "challenge/" + userName;
        String imgName = (fileName).replace(File.separatorChar, '/');
        try {
            amazonS3Client.deleteObject(bucket, imgName);
        }
        catch(AmazonS3Exception e) {
            log.error(e.toString());
        }
    }

}

