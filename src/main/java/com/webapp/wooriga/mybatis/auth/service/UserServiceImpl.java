package com.webapp.wooriga.mybatis.auth.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.exception.NoInformationException;
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

	@Override
	public ArrayList<UserInfo> sendUserInfo(String familyId) throws RuntimeException {
		List<User> userInfoList = dao.selectfamilyId(familyId);
		return this.sortwithUserName(userInfoList);
	}
	@Override
	public ArrayList<UserInfo> sortwithUserName(List<User> userInfoList) throws RuntimeException{
		ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();
		for(User user : userInfoList){
			UserInfo userInfo = new UserInfo(user.getRelationship(),user.getName(),user.getProfile(),user.getColor(),user.getUid());
			userInfoArrayList.add(userInfo);
		}
		if(userInfoArrayList.size() == 0) throw new NoInformationException();
		userInfoArrayList.sort((arg0,arg1)->{
			String name0 = arg0.getName();
			String name1 = arg1.getName();
			if(name0.equals(name1)) return 0;
			int i = 0;
			for(char ch : name0.toCharArray()) {
				if(ch == name1.charAt(i)) i++;
				else if (ch > name1.charAt(i)) return 1;
				else return -1;
			}
			return -1;
		});
		return userInfoArrayList;
	}

}