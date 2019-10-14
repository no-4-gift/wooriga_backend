package com.webapp.wooriga.mybatis.controller;

import com.webapp.wooriga.mybatis.service.calendar.CalendarService;
import com.webapp.wooriga.mybatis.service.result.CommonResult;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"2. Calendar"})
@RequestMapping(value = "/api")
@RestController
public class CalendarController {
    CalendarService calendarService;

    @Autowired
    public void CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @ApiOperation(value = "비어있는 날 입력", notes = "일정이 비어있는 날짜 입력")
    @PostMapping(value = "/id/date")
    public CommonResult insertDate(@ApiParam(value = "사용자의 비어있는 날 정보") @RequestBody EmptyDays emptyDays) {
        return calendarService.insertDayOnCalendar(emptyDays);
    }
}
