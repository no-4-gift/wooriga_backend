package com.webapp.wooriga.mybatis.calendar.dao;

import com.webapp.wooriga.mybatis.vo.EmptyDays;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface EmptyDaysDAO {

    void insertEmptyDay(EmptyDays emptyDays) throws Exception;
    List<EmptyDays> selectEmptyDay(@Param("familyId") String familyId, @Param("firstDate") String firstDate, @Param("finalDate") String finalDate);
    int selectToId(EmptyDays emptyDays);
    List<EmptyDays> selectToDate(HashMap<String,Object> emptyMap);
    void deleteToId(EmptyDays emptyDays);
}
