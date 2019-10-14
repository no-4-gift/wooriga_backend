package com.webapp.wooriga.mybatis.mapper;

import com.webapp.wooriga.mybatis.vo.EmptyDays;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmptyDaysMapper {
    void insert(EmptyDays emptyDays);
}
