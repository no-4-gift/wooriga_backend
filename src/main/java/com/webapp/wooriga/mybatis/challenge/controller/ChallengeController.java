package com.webapp.wooriga.mybatis.challenge.controller;

import com.webapp.wooriga.mybatis.challenge.service.ChallengeService;
import com.webapp.wooriga.mybatis.challenge.service.RegisteredChallengeService;
import com.webapp.wooriga.mybatis.vo.RegisteredInformation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

@Api(tags = {"2. Challenge"})
@RequestMapping(value = "/api")
@RestController
public class ChallengeController {
    private RegisteredChallengeService registeredChallengeService;
    private ChallengeService challengeService;

    @Autowired
    public ChallengeController(RegisteredChallengeService registeredChallengeService,ChallengeService challengeService) {
        this.registeredChallengeService = registeredChallengeService;
        this.challengeService = challengeService;
    }

    @ApiOperation(value = "챌린지 신청", notes = "챌린지 신청")
    @PostMapping(value = "/uid/date/cid")
    public void registerChallenge(@ApiParam(value = "챌린지 등록에 필요한 정보") @RequestBody RegisteredInformation registeredInformation, HttpServletResponse httpServletResponse) {
        registeredChallengeService.insertRegisteredChallenge(registeredInformation.getRegisteredChallenges(), registeredInformation.getParticipants()
                , registeredInformation.getCertifications(), httpServletResponse);
    }

    @ApiOperation(value = "챌린지 인증 등록(request : registeredFk(등록된 챌린지 번호),date(날짜),file(인증 사진))", notes = "챌린지 인증 등록(response : 200 - 성공, 400 - 잘못된 값 날림)")
    @PostMapping(value = "/registeredFk")
    public void certificateChallenge(@ApiParam(value = "인증 사진,날짜,등록된 챌린지 번호") @RequestParam(value ="file",required=false) MultipartFile file,
                                     @RequestParam Date date, @RequestParam long registeredFk,HttpServletResponse httpServletResponse){
        challengeService.certificateChallenge(registeredFk,date,file,httpServletResponse);
    }
}
