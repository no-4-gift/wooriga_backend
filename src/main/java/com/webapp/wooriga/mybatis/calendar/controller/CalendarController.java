package com.webapp.wooriga.mybatis.calendar.controller;

import com.webapp.wooriga.mybatis.calendar.result.CalendarInfo;
import com.webapp.wooriga.mybatis.calendar.service.CalendarService;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Api(tags = {"1. Calendar"})
@RequestMapping(value = "/api")
@RestController
public class CalendarController {
    private CalendarService calendarService;

    @Autowired
    public void CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @ApiOperation(value = "비어있는 날 입력", notes = "일정이 비어있는 날짜 입력")
    @PostMapping(value = "/uid/date")
    public void insertDate(@ApiParam(value = "사용자의 비어있는 날 정보") @RequestBody EmptyDays emptyDays, HttpServletResponse httpServletResponse) throws IOException{
         calendarService.insertDayOnCalendar(emptyDays,httpServletResponse);
    }

    @ApiOperation(value = "캘린더에 들어갈 정보 전달( request : familyId,year,month)", notes = "캘린더에 들어갈 정보 전달(response : date,name,relationship,color,profile)")
    @PostMapping(value = "/familyId/year/month")
    public CalendarInfo[] sendCalendarInfo(@RequestBody Map<String,String> family,HttpServletResponse httpServletResponse){
        return calendarService.selectCalendarInfo(family,httpServletResponse);
    }



}
