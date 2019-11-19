
package com.webapp.wooriga.mybatis.auth.service;

import java.util.ArrayList;
import java.util.List;

import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.vo.CodeUser;
import com.webapp.wooriga.mybatis.vo.User;

public interface UserService {

	public List<User> selectAll();
	public User selectOne(long uid);
	public void insert(User user);
	public void delete(User user);
	public void update(User user);
	public int checkUser(long uid);
	public String getCode(long uid);
	public void insertCodeUser(CodeUser codeuser);
	ArrayList<UserInfo> sendUserInfo(String familyId) throws RuntimeException;
	ArrayList<UserInfo> sortwithUserName(List<User> userInfoList) throws RuntimeException;
	int sortName(String name0,String name1);
}

