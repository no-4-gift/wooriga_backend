package com.webapp.wooriga.mybatis.auth.service;

import java.util.List;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.vo.CodeUser;
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
	public User selectOne(long uid) {
		return dao.selectOne(uid);
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

	@Override
	public int checkUser(long uid) {
		return dao.checkUser(uid);
	}

	@Override
	public String getCode(long uid) {
		return dao.getCode(uid);
	}

	@Override
	public void insertCodeUser(CodeUser codeuser) {
		dao.insertCodeUser(codeuser);
	}
}
