package com.webapp.wooriga.mybatis.mapper;

import java.util.List;

import com.webapp.wooriga.mybatis.vo.User;

public interface UserMapper {
	
	public List<User> selectAll();
	public User selectOne(long uid);
	public void insert(User user);
	public void delete(User user);
	public void update(User user);
	
}
