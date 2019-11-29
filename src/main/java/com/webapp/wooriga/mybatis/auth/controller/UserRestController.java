package com.webapp.wooriga.mybatis.auth.controller;

import java.util.*;

import com.webapp.wooriga.mybatis.auth.dao.CodeUserDAO;
import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.auth.service.KakaoService;
import com.webapp.wooriga.mybatis.auth.service.MyPageService;
import com.webapp.wooriga.mybatis.auth.service.UserService;
import com.webapp.wooriga.mybatis.vo.User;
import io.swagger.annotations.Api;
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
    private UserDAO userDAO;
    private KakaoService kakaoService;
    private CodeUserDAO codeUserDAO;

    @Autowired
    public UserRestController(UserDAO userDAO,UserService userService, KakaoService kakaoService, CodeUserDAO codeUserDAO) {
        this.userService = userService;
        this.userDAO = userDAO;
        this.kakaoService = kakaoService;
        this.codeUserDAO = codeUserDAO;
    }

    // 카카오 로그인 후
    @RequestMapping(value = "/social/login/kakao", method = RequestMethod.POST)
    public Map login(@RequestBody long id, @RequestBody String nickname, @RequestBody String profile) {
        //System.out.println(code);
        //log.error("code : " + code);
        //access_token = kakaoService.getAccessToken(code);
        //access_token = accessToken;
        //log.error("access : " + access_token);
        HashMap<String, Object> map = new HashMap<>();
        //HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_token);
        //System.out.println("login Controller : " + userInfo);
        //long id = (long)userInfo.get("id");
        //String nickname = (String)userInfo.get("nickname");
        //String image = (String)userInfo.get("profile");
        //String email = (String)userInfo.get("email");
        //String birthday = (String)userInfo.get("birth");
        //String color = "black";

        String image = profile;
        String email = "";
        String birthday = "";
        String color = "black";

        System.out.println("User info : " + id + ", " + nickname + ", " + image + ", " + email + ", " + birthday + ", " + color);
        System.out.println(userDAO.selectOne(id));
        if(userDAO.selectOne(id) == null) {
            userDAO.insert(new User(id, nickname, email, image, color, birthday));
            map.put("firstLogin", true);
        } else {
            userDAO.update(new User(id, nickname, email, image, color, birthday));
            map.put("firstLogin", false);
        }

        map.put("uid", id);
        /*
        String family_id = userService.checkFamilyId(id);
        if(family_id != null) { // 이미 존재하면
            map.put("isFamily", 1);
            map.put("code", family_id);

            List<User> userList = new ArrayList<>();
            userList = userService.familyAll(family_id);
            for (int i = 0; i < userList.size(); i++) {
                if(userList.get(i).getUid() == id) {
                    userList.remove(i);
                    break;
                }
            }
            map.put("familyList", userList);
        }
        else {
            map.put("isFamily", 0);
        }
        */

        return map;
    }

    // 메인
    @RequestMapping(value = "/main/{uid}", method = RequestMethod.GET)
    public Map main(@PathVariable long uid) {
        HashMap<String, Object> map = new HashMap<>();
        //uid를 통해 family_id 저장 여부 확인
        String family_id = userDAO.checkFamilyId(uid);

        if (family_id != null) { // 캘린더 화면 이동
            map.put("isFamily", 1);
            map.put("familyId", family_id);
        } else { // 최초 상태
            map.put("isFamily", 0);
        }

        return map;
    }

    @RequestMapping(value = "/family", method = RequestMethod.GET)
    public Map family(@RequestParam long uid, @RequestParam String code) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        User user = userDAO.selectOne(uid);

        try {
            //user.setFamilyId(code);
            //userService.updateFamilyId(user);

            List<User> userList = new ArrayList<>();
            List<String> colorList = new ArrayList<>();
            userList = userDAO.familyAll(code);
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUid() == uid) {
                    continue;
                } else {
                    colorList.add(userList.get(i).getColor());
                }
            }
            User userInfo = userDAO.selectOne(uid);
            //long managerUid = userService.getUid(code);
           // User manager = userService.selectOne(managerUid);

            map.put("userInfo", userInfo);
            //map.put("manager", manager);
            map.put("familyCount", userList.size());
            map.put("colorList", colorList);
        } catch (Exception e) {
        }

        return map;
    }

    @RequestMapping(value = "/family/{uid}", method = RequestMethod.PUT)
    public Map changeColor(@PathVariable long uid, @RequestBody String color, @RequestBody String relationship, @RequestBody String code) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        User user = userDAO.selectOne(uid);

        try {
            user.setFamilyId(code);
            user.setColor(color);
            user.setRelationship(relationship);
            userDAO.updateFamilyId(user);
            userDAO.update(user);

            map.put("familyId", code);
        } catch (Exception e) {
        }

        return map;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public Map admin(@RequestParam long uid, @RequestParam String accessToken) throws RuntimeException {
        User user = userDAO.selectOne(uid);
        return userService.admin(user, accessToken);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> allUsers() {
        return userDAO.selectAll();
    }

    // 마이페이지
    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    public Map mypage(@RequestParam long uid) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            // 로그인한 유저 정보
            User user = userDAO.selectOne(uid);

            // 가족 정보
            List<User> userList = new ArrayList<>();
            userList = userDAO.familyAll(user.getFamilyId());
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUid() == uid) {
                    userList.remove(i);
                    break;
                }
            }

            // 내 기록 정보


            map.put("userInfo", user);
            map.put("familyList", userList);

        } catch (Exception e) {

        }

        return map;
    }

    @RequestMapping(value = "/mypage/modify", method = RequestMethod.GET)
    public Map modify(@RequestParam long uid, @RequestParam String code) {
        HashMap<String, Object> map = new HashMap<>();
        User user = userDAO.selectOne(uid);
        try{
            List<User> userList = new ArrayList<>();
            List<String> colorList = new ArrayList<>();
            userList = userDAO.familyAll(code);
            for (int i = 0; i < userList.size(); i++) {
                if(userList.get(i).getUid() == uid) {
                    continue;
                }
                else {
                    colorList.add(userList.get(i).getColor());
                }
            }
            map.put("userInfo", user);
            map.put("colorList", colorList);
        } catch (Exception e) {
        }

        return map;
    }

    //내 정보 수정
    @RequestMapping(value = "/mypage/modify/{uid}", method = RequestMethod.PUT)
    public Map modifyUserInfo(@PathVariable long uid, @RequestBody User user) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if(user.getUid() == uid) {
                userDAO.updateMyInfo(user);
            }
            map.put("familyId", user.getFamilyId());
            map.put("success", true);
        }catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }

    //가족 추가
    @RequestMapping(value = "/mypage/add", method = RequestMethod.GET)
    public Map myFamily(@RequestParam long uid, @RequestParam String code) {

        HashMap<String, Object> map = new HashMap<>();
        User user = userDAO.selectOne(uid);
        try{
            List<User> userList = new ArrayList<>();
            userList = userDAO.familyAll(code);
            for (int i = 0; i < userList.size(); i++) {
                if(userList.get(i).getUid() == uid) {
                    userList.remove(i);
                    break;
                }
            }
            map.put("userInfo", user);
            map.put("familyList", userList);
        } catch (Exception e) {
        }

        return map;
    }

    @RequestMapping(value = "/mypage/add/{uid}", method = RequestMethod.PUT)
    public Map addFamily(@PathVariable long uid) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            User user = userDAO.selectOne(uid);
            map.put("code", user.getFamilyId());
        }catch (Exception e) {
            map.put("code", null);
        }
        return map;
    }

    // 가족 삭제
    @RequestMapping(value = "/mypage/delete/{uid}", method = RequestMethod.DELETE)
    public Map deleteFamily(@PathVariable long uid, @RequestBody long deleteId) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            String code = codeUserDAO.getCode(uid);
            if(code != null) { // 관리자의 경우에만
                User user = userDAO.selectOne(deleteId);
                String reset = "";
                user.setFamilyId(reset);
                userDAO.updateFamilyId(user);
                map.put("code", code);
            }
        }catch (Exception e) {
            map.put("code", null);
        }
        return map;
    }

    //관리자 위임
    @RequestMapping(value = "/mypage/manager", method = RequestMethod.PUT)
    public Map modifyManager() {
        HashMap<String, String> map = new HashMap<String, String>();
        // update family set uid = #{id} where code = #{code}
        try {
            // 관리자 modify 함수 만들기
            map.put("success", "true");
        } catch (Exception e) {
            map.put("success", "false");
        }
        return map;
    }


}