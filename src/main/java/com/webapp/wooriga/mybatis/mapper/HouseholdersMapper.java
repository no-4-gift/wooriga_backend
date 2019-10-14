package com.webapp.wooriga.mybatis.mapper;

import com.webapp.wooriga.mybatis.vo.Householders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseholdersMapper {
    void insertHouseholder(Householders householders);
}
