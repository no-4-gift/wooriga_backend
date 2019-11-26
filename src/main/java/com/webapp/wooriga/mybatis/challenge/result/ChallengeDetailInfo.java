package com.webapp.wooriga.mybatis.challenge.result;

import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.vo.Challenges;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;

@Data
public class ChallengeDetailInfo {
    private ArrayList<CertificationInfo> certificationInfoArrayList;
    private String resolution;
    private String summary;
    private String content;
    private String title;
    private Boolean certificationAvailableTrue;

    public ChallengeDetailInfo(String title,ArrayList<CertificationInfo> certificationInfoArrayList,String resolution,String summary,String content){
        this.title = title;
        this.certificationInfoArrayList = certificationInfoArrayList;
        this.summary = summary;
        this.content = content;
        this.resolution = resolution;
    }
}
