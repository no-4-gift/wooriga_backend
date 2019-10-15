package com.webapp.wooriga.mybatis.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.webapp.wooriga.mybatis.service.calendar.CalendarService;
import com.webapp.wooriga.mybatis.service.result.CommonResult;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import com.webapp.wooriga.mybatis.vo.RegisteredInformation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(tags = {"2. Calendar"})
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
    public CommonResult insertDate(@ApiParam(value = "사용자의 비어있는 날 정보") @RequestBody EmptyDays emptyDays) throws IOException{
        return calendarService.insertDayOnCalendar(emptyDays);
    }
    @ApiOperation(value = "챌린지 신청", notes = "챌린지 신청")
    @PostMapping(value = "/uid/date/cid")
    public CommonResult RegisterChallenge(@ApiParam(value = "챌린지 등록에 필요한 정보") @RequestBody RegisteredInformation registeredInformation){
        return calendarService.insertRegisteredChallenge(registeredInformation.getRegisteredChallenges(),registeredInformation.getParticipants()
        ,registeredInformation.getRegisteredDates());
    }
}
