package com.webapp.woriga.mybatis.dao;

import java.util.List;

import com.webapp.woriga.mybatis.vo.User;

public interface UserDAO {
	public List<User> selectAll();
	public User selectOne(String email);
	public void insert(User user);
	public void delete(User user);
	public void update(User user);
	
}
