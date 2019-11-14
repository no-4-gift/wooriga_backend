package com.webapp.wooriga.mybatis.challenge.controller;

import com.webapp.wooriga.mybatis.challenge.result.*;
import com.webapp.wooriga.mybatis.challenge.service.ChallengeService;
import com.webapp.wooriga.mybatis.challenge.service.ChallengeViewService;
import com.webapp.wooriga.mybatis.challenge.service.RegisteredChallengeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;

@Api(tags = {"2. Challenge"})
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"*"})
@RestController
public class ChallengeController {
    private RegisteredChallengeService registeredChallengeService;
    private ChallengeService challengeService;
    private ChallengeViewService challengeViewService;

    @Autowired
    public ChallengeController(ChallengeViewService challengeViewService,RegisteredChallengeService registeredChallengeService, ChallengeService challengeService) {
        this.registeredChallengeService = registeredChallengeService;
        this.challengeService = challengeService;
        this.challengeViewService = challengeViewService;
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

    @ApiOperation(value = "챌린지 인증 등록(request : 등록된 챌린지 번호,날짜,인증 사진)", notes = "response : 200 - 성공," +
            " 409 - 전송 데이터 중 존재하지 않는 데이터를 포함하고 있어 db에 저장되지 않음 411 - 조건에 맞지 않음 422- 내부 문제로 인해 오류")
    @PostMapping(value = "/registeredFk" , consumes = "multipart/form-data")
    public void certificateChallenge(@RequestParam(value ="file",required=false) MultipartFile file,
                                     @RequestParam String date, @RequestParam long registeredFk) throws RuntimeException{
        challengeService.certificateChallenge(registeredFk,date,file);
    }

    @ApiOperation(value = "각오의 한마디 전달", notes = "response : 200 - 성공 ")
    @PostMapping(value = "/familyId/registeredFk")
    public String conveyResolution(@RequestBody Map<String,Object> info) throws RuntimeException{
        return registeredChallengeService.conveyResolution(info);
    }

    @ApiOperation(value = "도전중인 챌린지 정보 전달", notes = "response: 200 - 성공 411 - 조건과 맞는 데이터가 없음")
    @GetMapping(value = "/familyId/uid")
    public ArrayList<ChallengeBarInfo> conveyMyChallengeInfo(@RequestParam String familyId, @RequestParam long uid) throws RuntimeException{
        return challengeViewService.sendChallengeViewInfo(false,familyId,uid);
    }
    @ApiOperation(value = "함께하는 챌린지 정보 전달", notes = "response: 200 - 성공 411 - 조건과 맞는 데이터가 없음")
    @GetMapping(value = "/familyId/uid/bool")
    public ArrayList<ChallengeBarInfo> conveyOurChallengeInfo(@RequestParam String familyId, @RequestParam long uid) throws RuntimeException {
        return challengeViewService.sendChallengeViewInfo(true, familyId, uid);
    }
    @ApiOperation(value = "챌린지 디테일 정보 전달(프로필 사진 제외)", notes = "response : 200 - 성공 411 - 조건과 맞는 데이터가 없음")
    @GetMapping(value = "/uid/registeredId")
    public ChallengeDetailInfo conveyChallengeDetailInfo(@RequestParam long uid, @RequestParam long registeredId){
        return challengeViewService.sendChallengeDetailInfo(uid,registeredId);
    }
    @ApiOperation(value = "챌린지 디테일 내 참여자 프로필 사진 전달", notes = "response : 200 - 성공 411 - 조건과 맞는 데이터가 없음")
    @GetMapping(value = "/registeredId")
    public ArrayList<UserInfo> conveyParticipantsInfo(@RequestParam long registeredId){
        return challengeViewService.sendParticipantsInfo(registeredId);
    }
}
