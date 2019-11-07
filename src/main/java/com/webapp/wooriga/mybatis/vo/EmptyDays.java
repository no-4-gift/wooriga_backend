package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.webapp.wooriga.mybatis.calendar.deserializer.EmptyDaysDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@JsonDeserialize(using = EmptyDaysDeserializer.class)
public class EmptyDays {
    private String familyIdFk;
    private long userIdFk;
    private Date emptydate;

    public EmptyDays(String familyIdFk, long userIdFk, Date emptydate){
        this.familyIdFk = familyIdFk;
        this.userIdFk = userIdFk;
        this.emptydate = emptydate;
    }
    public EmptyDays(){

    }
}
