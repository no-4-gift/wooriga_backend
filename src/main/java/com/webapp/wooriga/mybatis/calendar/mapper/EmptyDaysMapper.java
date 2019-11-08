package com.webapp.wooriga.mybatis.calendar.mapper;

import com.webapp.wooriga.mybatis.vo.EmptyDays;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface EmptyDaysMapper {
    void insert(EmptyDays emptyDays);
    List<EmptyDays> selectList(@Param("familyId")String familyId, @Param("firstDate")String firstDate, @Param("finalDate") String finalDate);
    int selectToId(EmptyDays emptyDays);
}
