package com.webapp.wooriga.mybatis.dao;

import com.webapp.wooriga.mybatis.mapper.EmptyDaysMapper;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class EmptyDaysDAOImpl {

    private EmptyDaysMapper emptyDaysMapper;
    @Autowired
    public EmptyDaysDAOImpl(EmptyDaysMapper emptyDaysMapper){
        this.emptyDaysMapper = emptyDaysMapper;
    }


    public void insertEmptyDay(EmptyDays emptyDays) throws SQLException,Exception {
        emptyDaysMapper.insert(emptyDays);
    }

    public List<EmptyDays> selectEmptyDay(@Param("familyId")String familyId, @Param("firstDate")String firstDate, @Param("finalDate") String finalDate){
        return emptyDaysMapper.selectList(familyId,firstDate,finalDate);
    }
}
