package com.webapp.wooriga.mybatis.auth.service;

import com.webapp.wooriga.mybatis.auth.result.MyRecordInfo;
import com.webapp.wooriga.mybatis.calendar.service.CalendarModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {

    private CalendarModuleService calendarModuleService;
    @Autowired
    public MyPageService(CalendarModuleService calendarModuleService){
        this.calendarModuleService = calendarModuleService;
    }
    public MyPageService(){

    }
    public MyRecordInfo sendMyRecordInfo(){
        MyRecordInfo myRecordInfo = new MyRecordInfo();

        //calendarModuleService.setChallengeBarInfoList()
        return myRecordInfo;
    }
}
