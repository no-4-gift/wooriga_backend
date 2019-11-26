package com.webapp.wooriga.mybatis.auth.controller;

import java.util.*;

import com.webapp.wooriga.mybatis.auth.result.MyRecordInfo;
import com.webapp.wooriga.mybatis.auth.service.KakaoService;
import com.webapp.wooriga.mybatis.auth.service.MyPageService;
import com.webapp.wooriga.mybatis.auth.service.UserService;
import com.webapp.wooriga.mybatis.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = {"0.회원가입,3. 마이페이지"})
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"*"})
@RestController
public class UserRestController {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private UserService userService;
    private KakaoService kakaoService;
    private MyPageService myPageService;

    @Autowired
    public UserRestController(UserService userService,KakaoService kakaoService,MyPageService myPageService){
        this.userService = userService;
        this.kakaoService =kakaoService;
        this.myPageService = myPageService;
    }
    static String access_token;

    // 카카오 로그인 후
    @RequestMapping(value = "/social/login/kakao", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView mav, @RequestParam String code) {
        log.error("code : " + code);
        access_token = kakaoService.getAccessToken(code);
        log.error("access : " + access_token);
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_token);
        //System.out.println("login Controller : " + userInfo);
        long id = (long)userInfo.get("id");
        String nickname = (String)userInfo.get("nickname");
        String image = (String)userInfo.get("profile");
        String email = (String)userInfo.get("email");
        String birthday = (String)userInfo.get("birth");
        String color = "black";

        //System.out.println("User info : " + id + ", " + nickname);
        //System.out.println(userService.selectOne(id));
        userService.update(new User(id, nickname, email, image, color, birthday));

        mav.addObject("access_token", access_token);
        mav.addObject("user", userInfo);
        mav.setViewName("main");
        return mav;
    }

    // 메인
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public List<User> main(@RequestBody User user, @RequestBody String accessToken) {

        List<User> userList = new ArrayList<>();

        access_token = accessToken;
        long uid = user.getUid();

        //uid를 통해 family_id 저장 여부 확인
        String family_id = userService.checkFamilyId(uid);

        if(family_id != null) { // 캘린더 화면 이동
            userList = userService.familyAll(family_id);
            for (int i = 0; i < userList.size(); i++) {
                if(userList.get(i).getUid() == uid) {
                    userList.remove(i);
                    break;
                }
            }
        }
        else { // 최초 상태
        }

        return userList;
    }

    @RequestMapping(value = "/family", method = RequestMethod.PUT)
    public Map family(@RequestBody User user, @RequestBody String code) {
        HashMap<String, String> map = new HashMap<String, String>();
        user.setFamilyId(code);
        userService.updateFamilyId(user);
        map.put("success", "true");
        return map;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public Map admin(@RequestBody User user, @RequestBody String accessToken) throws RuntimeException {
        return userService.admin(user, accessToken);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> allUsers() {
        return userService.selectAll();
    }

    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public List<User> mypage() {

        return null;
    }

    @ApiOperation(value = "마이페이지 내 나의 기록 전달" )
    @GetMapping(value = "/mypage/familyId/uid")
    public MyRecordInfo sendMyRecord(){
        return myPageService.sendMyRecordInfo();
    }

   /*
   @RequestMapping(value = "/users", method = RequestMethod.POST)
   public void insertUser(@RequestBody User user) {
      try {
            userService.insert(user);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   */
}