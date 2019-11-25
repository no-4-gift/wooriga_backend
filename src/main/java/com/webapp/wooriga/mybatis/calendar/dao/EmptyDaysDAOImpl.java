package com.webapp.wooriga.mybatis.calendar.dao;

import com.webapp.wooriga.mybatis.calendar.mapper.EmptyDaysMapper;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Component
public class EmptyDaysDAOImpl implements EmptyDaysDAO{

    private EmptyDaysMapper emptyDaysMapper;

    @Autowired
    public EmptyDaysDAOImpl(EmptyDaysMapper emptyDaysMapper) {
        this.emptyDaysMapper = emptyDaysMapper;
    }

    @Override
    public void insertEmptyDay(EmptyDays emptyDays) throws Exception {
        emptyDaysMapper.insert(emptyDays);
    }

    @Override
    public List<EmptyDays> selectEmptyDay(@Param("familyId") String familyId, @Param("firstDate") String firstDate, @Param("finalDate") String finalDate) {
        return emptyDaysMapper.selectList(familyId, firstDate, finalDate);
    }
    @Override
    public int selectToId(EmptyDays emptyDays){
        return emptyDaysMapper.selectToId(emptyDays);
    }

    @Override
    public List<EmptyDays> selectToDate(HashMap<String,Object> emptyMap){
        return emptyDaysMapper.selectToDate(emptyMap);
    }
    @Override
    public void deleteToId(EmptyDays emptyDays){
        emptyDaysMapper.deleteToId(emptyDays);
    }
}
