package com.webapp.wooriga.mybatis.mapper;

import com.webapp.wooriga.mybatis.vo.RegisteredDates;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisteredDatesMapper {
    void insertRegisteredDates(RegisteredDates registeredDates);
}
