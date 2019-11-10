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
public class EmptyDaysDAOImpl {

    private EmptyDaysMapper emptyDaysMapper;

    @Autowired
    public EmptyDaysDAOImpl(EmptyDaysMapper emptyDaysMapper) {
        this.emptyDaysMapper = emptyDaysMapper;
    }


    public void insertEmptyDay(EmptyDays emptyDays) throws Exception {
        emptyDaysMapper.insert(emptyDays);
    }

    public List<EmptyDays> selectEmptyDay(@Param("familyId") String familyId, @Param("firstDate") String firstDate, @Param("finalDate") String finalDate) {
        return emptyDaysMapper.selectList(familyId, firstDate, finalDate);
    }
    public int selectToId(EmptyDays emptyDays){
        return emptyDaysMapper.selectToId(emptyDays);
    }

    public List<EmptyDays> selectToDate(HashMap<String,Object> emptyMap){
        return emptyDaysMapper.selectToDate(emptyMap);
    }
}
