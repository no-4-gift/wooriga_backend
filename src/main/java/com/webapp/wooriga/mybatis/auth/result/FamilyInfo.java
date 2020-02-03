package com.webapp.wooriga.mybatis.auth.result;

import lombok.Data;

@Data
public class FamilyInfo {
    private Long uid;
    private String color;
    private String relationship;
    private String code;
    public FamilyInfo(Long uid, String color,String relationship,String code){
        this.uid = uid;
        this.code = code;
        this.color = color;
        this.relationship = relationship;
    }
}
