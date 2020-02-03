package com.webapp.wooriga.mybatis.auth.controller;

import java.util.*;

import com.webapp.wooriga.mybatis.auth.dao.CodeUserDAO;
import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.auth.result.FamilyInfo;
import com.webapp.wooriga.mybatis.auth.service.UserService;
import com.webapp.wooriga.mybatis.exception.NoInformationException;
import com.webapp.wooriga.mybatis.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"0.회원가입,3. 마이페이지"})
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"*"})
@RestController
public class UserRestController {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "회원가입 다음 가족 추가, 컬러, 관계 추가(requestBody : uid,color,relationship,code)",notes = "value : 200 - 성공, 404 - 그런 유저 없음")
    @RequestMapping(value = "/family/uid/color/relationship/code", method = RequestMethod.PUT)
    public HashMap<String,String> changeColor(@RequestBody FamilyInfo familyInfo) throws RuntimeException {
        return userService.changeColorAndEtc(familyInfo);
    }

}