package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.webapp.wooriga.mybatis.calendar.deserializer.EmptyDaysDeserializer;
import lombok.*;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = EmptyDaysDeserializer.class)
public class EmptyDays {
    private String familyId;
    private long userIdFk;
    private Date emptydate;
}
