package com.webapp.wooriga.mybatis.dao;

import com.webapp.wooriga.mybatis.mapper.HouseholdersMapper;
import com.webapp.wooriga.mybatis.vo.Householders;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HouseholdersDAOImpl {
    @Autowired
    SqlSessionTemplate mybatis;

    public void insertHouseholder(Householders householders){
        HouseholdersMapper householdersMapper = mybatis.getMapper(HouseholdersMapper.class);
        householdersMapper.insertHouseholder(householders);
    }
}
