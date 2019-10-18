package com.webapp.wooriga.mybatis.service;

import java.util.List;

import com.webapp.wooriga.mybatis.vo.User;

public interface UserService {
	
	public List<User> selectAll();
	public User selectOne(long uid);
	public void insert(User user);
	public void delete(User user);
	public void update(User user);
	
}
