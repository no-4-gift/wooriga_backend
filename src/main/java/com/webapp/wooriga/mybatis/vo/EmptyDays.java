package com.webapp.wooriga.mybatis.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class EmptyDays {
    private long familyIdFk;
    private String userIdFk;
    private Date emptydate;

    public EmptyDays(long familyIdFk, String userIdFk, Date emptydate){
        this.familyIdFk = familyIdFk;
        this.userIdFk = userIdFk;
        this.emptydate = emptydate;
    }
}
