package com.webapp.wooriga.mybatis.challenge.result;

import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.vo.Challenges;
import lombok.Builder;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;

@Data
@Builder
public class ChallengeDetailInfo {
    private ArrayList<CertificationInfo> certificationInfoArrayList;
    private String resolution;
    private String summary;
    private String content;
    private String title;
    private Boolean certificationAvailableTrue;
}
