package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.webapp.wooriga.mybatis.calendar.deserializer.EmptyDaysDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
@JsonDeserialize(using = EmptyDaysDeserializer.class)
public class EmptyDays {
    private String familyId;
    private long userIdFk;
    private Date emptydate;
}
