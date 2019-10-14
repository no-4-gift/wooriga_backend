package com.webapp.wooriga.mybatis.dao;

import com.webapp.wooriga.mybatis.mapper.EmptyDaysMapper;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

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
}
