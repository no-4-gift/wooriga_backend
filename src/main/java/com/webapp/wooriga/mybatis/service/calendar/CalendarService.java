package com.webapp.wooriga.mybatis.service.calendar;

import com.webapp.wooriga.mybatis.dao.EmptyDaysDAOImpl;
import com.webapp.wooriga.mybatis.mapper.EmptyDaysMapper;
import com.webapp.wooriga.mybatis.service.result.CommonResult;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CalendarService {
    EmptyDaysDAOImpl emptyDaysDAOImpl;
    Logger log = LoggerFactory.getLogger(CalendarService.class);
    @Autowired
    public CalendarService(EmptyDaysDAOImpl emptyDaysDAOImpl){
        this.emptyDaysDAOImpl = emptyDaysDAOImpl;
    }

    public CommonResult insertDayOnCalendar(EmptyDays emptyDays){
        CommonResult commonResult = new CommonResult();
        commonResult.setSuccessResult(200,"success");
        try {
            emptyDaysDAOImpl.insertEmptyDay(emptyDays);
        }
        catch(Exception e){
            commonResult.setFailResult(404,e.toString());
            log.error(e.toString());
        }
        return commonResult;
    }
}
