package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.webapp.wooriga.mybatis.controller.deserializer.EmptyDaysDeserializer;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Getter
@Setter
@JsonDeserialize(using = EmptyDaysDeserializer.class)
public class EmptyDays {
    private long familyIdFk;
    private String userIdFk;
    private Date emptydate;

    public EmptyDays(long familyIdFk, String userIdFk, Date emptydate){
        this.familyIdFk = familyIdFk;
        this.userIdFk = userIdFk;
        this.emptydate = emptydate;
    }
    public EmptyDays(){

    }
}
