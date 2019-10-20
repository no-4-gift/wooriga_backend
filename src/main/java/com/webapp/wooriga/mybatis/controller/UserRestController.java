package com.webapp.wooriga.mybatis.controller;

import java.util.List;
import java.util.Random;

import com.webapp.wooriga.mybatis.service.UserService;
import com.webapp.wooriga.mybatis.vo.CodeUser;
import com.webapp.wooriga.mybatis.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

	@Autowired
	UserService service;

    @RequestMapping(value = "/admin/{uid}", method = RequestMethod.GET)
    public String admin(@PathVariable long uid) {

        // 랜덤코드 테이블에서 현재 관리자 코드 존재 여부 확인
        int check = service.checkUser(uid);

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

            service.insertCodeUser(new CodeUser(uid, code));
            System.out.println(uid + "님의 생성 코드 : " + code);
        }

        return "admin";
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
