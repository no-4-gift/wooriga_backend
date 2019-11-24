package com.webapp.wooriga.mybatis.calendar.controller;

import com.webapp.wooriga.mybatis.auth.service.UserService;
import com.webapp.wooriga.mybatis.calendar.result.CalendarInfo;
import com.webapp.wooriga.mybatis.calendar.result.EmptyDayUserInfo;
import com.webapp.wooriga.mybatis.calendar.service.CalendarService;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeInfo;
import com.webapp.wooriga.mybatis.challenge.result.RegisteredInformation;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.challenge.service.ChallengeService;
import com.webapp.wooriga.mybatis.challenge.service.RegisteredChallengeService;
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
    private RegisteredChallengeService registeredChallengeService;
    private ChallengeService challengeService;

    @Autowired
    public void CalendarController(RegisteredChallengeService registeredChallengeService,ChallengeService challengeService,UserService userService,CalendarService calendarService) {
        this.calendarService = calendarService;
        this.userService = userService;
        this.challengeService = challengeService;
        this.registeredChallengeService = registeredChallengeService;
    }

    @ApiOperation(value = "비어있는 날 입력", notes = "일정이 비어있는 날짜 입력 (response :200 - 성공 409 - 날짜가 겹쳤거나 유저 아이디가 존재하지 않아 저장되지않음 411 - 가족 아이디가 존재하지 않는 아이디라 저장되지 않음)")
    @PostMapping(value = "/uid/date")
    public void insertDate(@ApiParam(value = "사용자의 비어있는 날 정보") @RequestBody EmptyDays emptyDays) throws IOException{
         calendarService.insertDayOnCalendar(emptyDays);
    }

    @ApiOperation(value = "캘린더에 들어갈 정보 전달( request : familyId,year,month)", notes = "캘린더에 들어갈 정보 전달(response : 200 - 성공 ")
    @GetMapping(value = "/familyId/year/month")
    public CalendarInfo sendCalendarInfo(@RequestParam String familyId, @RequestParam String year, @RequestParam String month){
        return calendarService.sendCalendarInfo(familyId,year,month);
    }

    @ApiOperation(value = " 일정 삭제", notes ="response 200 - 성공 409 - 삭제 실패 400- date가 알맞지 않은 형식임 411 - 알맞지 않은 조건임" )
    @DeleteMapping(value = "/uid/date")
    public void deleteCalendarInfo (@RequestBody EmptyDays emptyDays){
        calendarService.deleteCalendarInfo(emptyDays);
    }

    @ApiOperation(value = "소속된 멤버들 정보(request : familyId)",notes="response 200 - 성공 404 - 없는 가족 번호")
    @GetMapping(value = "/familyId")
    public ArrayList<UserInfo> sendUserInfo(@RequestParam String familyId){
        return userService.sendUserInfo(familyId);
    }
    @ApiOperation(value = "챌린지 신청", notes = "response : 200 - 성공 409 - 전송 데이터 중 존재하지 않는 데이터를 포함하고 있어 db에 저장되지 않음 411 - 조건에 맞지 않음")
    @PostMapping(value = "/uid/date/cid")
    public void registerChallenge(@ApiParam(value = "챌린지 등록에 필요한 정보") @RequestBody RegisteredInformation registeredInformation) throws RuntimeException {
        registeredChallengeService.insertRegisteredChallenge(registeredInformation.getRegisteredChallenges(), registeredInformation.getParticipants()
                , registeredInformation.getCertifications());
    }

    @ApiOperation(value="챌린지 신청할 때 필요한 정보(챌린지 리스트, 그 date에 가능하다고 선택한 멤버들 정보)", notes= "response : 200 - 성공 411 - 조건에 맞지 않아 결과가 없음")
    @PostMapping(value = "/familyId/dateList")
    public ChallengeInfo sendChallengeInfo(@RequestBody Map<String,Object> info) throws RuntimeException{
        return challengeService.sendChallengeInfo((ArrayList<String>)info.get("date"),(String)info.get("familyId"));
    }


}
