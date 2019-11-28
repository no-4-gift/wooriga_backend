package com.webapp.wooriga.mybatis.auth.controller;

import com.webapp.wooriga.mybatis.auth.result.MyRecordInfo;
import com.webapp.wooriga.mybatis.auth.service.MyPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"3.my page"})
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"*"})
@RestController
public class MyPageController {
    private MyPageService myPageService;
    public MyPageController(){}
    @Autowired
    public MyPageController(MyPageService myPageService){
        this.myPageService = myPageService;
    }

    @ApiOperation(value = "마이페이지 내부 나의 기록 전달,현재 진행 중인 챌린지 전달",notes = "response : 200 - 성공 , 411 -이에 맞는 정보가 없음")
    @GetMapping(value = "/mypage/familyId/uid")
    public MyRecordInfo sendMyRecord(@RequestParam("familyId") String familyId, @RequestParam("uid") long uid) throws RuntimeException {
        return myPageService.sendMyRecordInfo(familyId, uid);
    }
}
