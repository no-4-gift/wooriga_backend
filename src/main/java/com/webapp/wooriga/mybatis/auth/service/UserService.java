
package com.webapp.wooriga.mybatis.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.webapp.wooriga.mybatis.auth.result.FamilyInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.vo.CodeUser;
import com.webapp.wooriga.mybatis.vo.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
	ArrayList<UserInfo> sendUserInfo(String familyId) throws RuntimeException;

	int sortName(String name0, String name1);

	HashMap<String, String> changeColorAndEtc(FamilyInfo familyInfo) throws RuntimeException;

	User loadUserByUsername(Long uid) throws RuntimeException;

	void updateUser(User user) throws RuntimeException;

	ArrayList<UserInfo> sortwithUserName(ArrayList<UserInfo> userInfoArrayList);

	User setUserColorAndEtc(long uid, String familyId, String color, String relationship);

	ArrayList<UserInfo> mergeUserInfoArraylist(ArrayList<UserInfo> userInfoArrayList, List<User> userInfoList, User chief);

}
