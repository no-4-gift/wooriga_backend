
package com.webapp.wooriga.mybatis.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.vo.CodeUser;
import com.webapp.wooriga.mybatis.vo.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
	ArrayList<UserInfo> sendUserInfo(String familyId) throws RuntimeException;
	int sortName(String name0,String name1);
	ArrayList<UserInfo> addUserInfoToArrayList(User chief,List<User> userInfoList);
	public Map admin(User user) throws RuntimeException;
}
