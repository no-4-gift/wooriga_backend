package com.webapp.wooriga.mybatis.controller;

import java.util.List;

import com.webapp.wooriga.mybatis.service.UserService;
import com.webapp.wooriga.mybatis.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

	@Autowired
	UserService service;
	
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
