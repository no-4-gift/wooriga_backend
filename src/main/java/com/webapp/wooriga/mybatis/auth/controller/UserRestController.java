package com.webapp.wooriga.mybatis.auth.controller;

import java.util.*;

import com.webapp.wooriga.mybatis.auth.service.KakaoService;
import com.webapp.wooriga.mybatis.auth.service.UserService;
import com.webapp.wooriga.mybatis.vo.CodeUser;
import com.webapp.wooriga.mybatis.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserRestController {

    @Autowired
    UserService userService;

    @Autowired
    KakaoService kakaoService;

    static String access_token;

    // 카카오 로그인 후
    @RequestMapping(value = "/social/login/kakao", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView mav, @RequestParam String code) {
        //System.out.println("code : " + code);
        access_token = kakaoService.getAccessToken(code);
        //System.out.println("access : " + access_token);
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_token);
        //System.out.println("login Controller : " + userInfo);
        long id = (long)userInfo.get("id");
        String nickname = (String)userInfo.get("nickname");
        //System.out.println("User info : " + id + ", " + nickname);

        if(userService.selectOne(id) == null)
            userService.insert(new User(id, nickname));
        else
            System.out.println("이미 DB에 존재");

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
    public String admin(@RequestBody User user, @RequestBody String accessToken) {

        // 랜덤코드 테이블에서 현재 관리자 코드 존재 여부 확인
        int check = userService.checkUser(user.getUid());
        String getCode = "";

        System.out.println("check : " + check);
        // 없으면 8자리 생성
        if(check != 1) {
            Random rnd = new Random();
            StringBuffer buf = new StringBuffer();

            for (int i = 0; i < 8; i++) {
                if (rnd.nextBoolean()) {
                    buf.append((char) ((int) (rnd.nextInt(26)) + 65));
                } else {
                    buf.append((rnd.nextInt(10)));
                }
            }

            String code = buf.toString();

            userService.insertCodeUser(new CodeUser(user.getUid(), code)); // codeUser에 저장
            user.setFamilyId(code);
            userService.updateFamilyId(user); // family_id 갱신
            System.out.println(user.getUid() + "님의 생성 코드 : " + code);
            getCode = code;
        }
        else {
            getCode = userService.getCode(user.getUid());
        }

        return getCode;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> allUsers() {
        return userService.selectAll();
    }

    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public List<User> mapage() {

        return null;
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