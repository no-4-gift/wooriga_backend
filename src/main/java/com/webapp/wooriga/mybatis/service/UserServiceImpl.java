package com.webapp.wooriga.mybatis.service;

import java.util.List;

import com.webapp.wooriga.mybatis.dao.UserDAO;
import com.webapp.wooriga.mybatis.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("service")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO dao;
	
	@Override
	public List<User> selectAll() {
		return dao.selectAll();
	}

	@Override
	public User selectOne(String email) {
		return dao.selectOne(email);
	}

	@Override
	public void insert(User user) {
		dao.insert(user);
	}

	@Override
	public void delete(User user) {
		dao.delete(user);
	}

	@Override
	public void update(User user) {
		dao.update(user);
	}

}
