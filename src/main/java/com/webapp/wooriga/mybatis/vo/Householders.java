package com.webapp.wooriga.mybatis.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Householders {
    private long familyId;
    private String householderId;

    public Householders(long familyId,String householderId){
        this.familyId = familyId;
        this.householderId = householderId;
    }
    public Householders(){}
}
