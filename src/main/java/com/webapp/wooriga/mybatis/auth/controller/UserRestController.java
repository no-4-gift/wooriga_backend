package com.webapp.wooriga.mybatis.auth.controller;

import java.util.*;

import com.webapp.wooriga.mybatis.auth.service.UserService;
import com.webapp.wooriga.mybatis.vo.CodeUser;
import com.webapp.wooriga.mybatis.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

	@Autowired
	UserService service;

	static String access_token;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
    public List<User> main(@RequestBody User user, @RequestBody String accessToken) {

        List<User> userList = new ArrayList<>();

	    access_token = accessToken;
	    long uid = user.getUid();

	    //uid를 통해 family_id 저장 여부 확인
        String family_id = service.checkFamilyId(uid);

        if(family_id != null) { // 캘린더 화면 이동
            userList = service.familyAll(family_id);
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

    @RequestMapping(value = "/family", method = RequestMethod.GET)
    public String family() {

	    return "";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(@RequestBody User user, @RequestBody String accessToken) {

        // 랜덤코드 테이블에서 현재 관리자 코드 존재 여부 확인
        int check = service.checkUser(user.getUid());
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

            service.insertCodeUser(new CodeUser(user.getUid(), code)); // codeUser에 저장
            user.setFamilyId(code);
            service.updateFamilyId(user); // family_id 갱신
            System.out.println(user.getUid() + "님의 생성 코드 : " + code);
            getCode = code;
        }
        else {
            getCode = service.getCode(user.getUid());
        }

        return getCode;
    }
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> allUsers() {
		return service.selectAll();
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public void insertUser(@RequestBody User user) {
		try {
			service.insert(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
