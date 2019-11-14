package com.webapp.wooriga.mybatis.calendar.controller;

import com.webapp.wooriga.mybatis.auth.service.UserService;
import com.webapp.wooriga.mybatis.calendar.result.CalendarInfo;
import com.webapp.wooriga.mybatis.calendar.result.EmptyDayUserInfo;
import com.webapp.wooriga.mybatis.calendar.service.CalendarService;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Api(tags = {"1. Calendar"})
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"*"})
@RestController
public class CalendarController {
    private CalendarService calendarService;
    private UserService userService;

    @Autowired
    public void CalendarController(UserService userService,CalendarService calendarService) {
        this.calendarService = calendarService;
        this.userService = userService;
    }

    @ApiOperation(value = "비어있는 날 입력", notes = "일정이 비어있는 날짜 입력 (response :200 - 성공 409 - 날짜가 겹쳐서 저장되지 않음)")
    @PostMapping(value = "/uid/date")
    public void insertDate(@ApiParam(value = "사용자의 비어있는 날 정보") @RequestBody EmptyDays emptyDays) throws IOException{
         calendarService.insertDayOnCalendar(emptyDays);
    }

    @ApiOperation(value = "캘린더에 들어갈 정보 전달( request : familyId,year,month)", notes = "캘린더에 들어갈 정보 전달(response : 200 - 성공 " +
            " 411 - 조건과 맞지않는 정보로 찾는데 실패함 )")
    @GetMapping(value = "/familyId/year/month")
    public CalendarInfo sendCalendarInfo(@RequestParam String familyId, @RequestParam String year, @RequestParam String month){
        return calendarService.selectCalendarInfo(familyId,year,month);
    }

    @ApiOperation(value = " 일정 삭제", notes ="response 200 - 성공 409 - 삭제 실패" )
    @DeleteMapping(value = "/uid/date")
    public void deleteCalendarInfo (@RequestBody EmptyDays emptyDays){
        calendarService.deleteCalendarInfo(emptyDays);
    }

    @ApiOperation(value = "소속된 멤버들 정보(request : familyId)",notes="response 200 - 성공 404 - 없는 가족 번호")
    @GetMapping(value = "/familyId")
    public ArrayList<UserInfo> sendUserInfo(@RequestParam String familyId){
        return userService.sendUserInfo(familyId);
    }

}
