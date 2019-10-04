package com.webapp.woriga.mybatis.service;

import java.util.List;

import com.webapp.woriga.mybatis.vo.User;

public interface UserService {
	
	public List<User> selectAll();
	public User selectOne(String email);
	public void insert(User user);
	public void delete(User user);
	public void update(User user);
	
}
