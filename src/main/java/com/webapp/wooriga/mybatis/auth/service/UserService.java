
package com.webapp.wooriga.mybatis.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.vo.CodeUser;
import com.webapp.wooriga.mybatis.vo.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

	public List<User> selectAll();
	public User selectOne(long uid);
	public void insert(User user);
	public void delete(User user);
	public void update(User user);
	public void updateFamilyId(User user);
	public List<User> familyAll(String family_id);
	public String checkFamilyId(long uid);
	public int checkUser(long uid);
	public String getCode(long uid);
	public void insertCodeUser(CodeUser codeuser);
	ArrayList<UserInfo> sendUserInfo(String familyId) throws RuntimeException;
	ArrayList<UserInfo> sortwithUserName(List<User> userInfoList) throws RuntimeException;
	int sortName(String name0,String name1);
	public Map admin(User user, String accessToken) throws RuntimeException;
}
