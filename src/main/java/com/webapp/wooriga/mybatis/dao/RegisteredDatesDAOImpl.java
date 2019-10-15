package com.webapp.wooriga.mybatis.dao;

import com.webapp.wooriga.mybatis.mapper.RegisteredDatesMapper;
import com.webapp.wooriga.mybatis.vo.RegisteredDates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegisteredDatesDAOImpl implements RegisteredDatesDAO {
    private RegisteredDatesMapper registeredDatesMapper;

    @Autowired
    RegisteredDatesDAOImpl(RegisteredDatesMapper registeredDatesMapper){
        this.registeredDatesMapper = registeredDatesMapper;
    }

    @Override
    public void insertRegisteredDate(RegisteredDates registeredDates){
        registeredDatesMapper.insertRegisteredDates(registeredDates);
    }
}
