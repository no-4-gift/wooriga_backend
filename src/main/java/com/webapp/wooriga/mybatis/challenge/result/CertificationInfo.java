package com.webapp.wooriga.mybatis.challenge.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CertificationInfo {
    private String certificationDate;
    private String cardDate;
    private String certificationImage;
    private int certificationTrue;
}
