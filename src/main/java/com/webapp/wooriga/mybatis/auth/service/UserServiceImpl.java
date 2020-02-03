package com.webapp.wooriga.mybatis.auth.service;

import java.lang.reflect.Array;
import java.util.*;

import com.webapp.wooriga.mybatis.auth.dao.CodeUserDAO;
import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.auth.result.FamilyInfo;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.challenge.service.MakeArrayListService;
import com.webapp.wooriga.mybatis.exception.NoInformationException;
import com.webapp.wooriga.mybatis.exception.NoStoringException;
import com.webapp.wooriga.mybatis.vo.CodeUser;
import com.webapp.wooriga.mybatis.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserServiceImpl implements UserService {
	Logger log = LoggerFactory.getLogger(this.getClass());
	private UserDAO userDAO;
	private CodeUserDAO codeUserDAO;
	private MakeArrayListService makeArrayListService;
	@Autowired
	public UserServiceImpl(UserDAO userDAO,CodeUserDAO codeUserDAO,MakeArrayListService makeArrayListService){
		this.userDAO = userDAO;
		this.codeUserDAO = codeUserDAO;
		this.makeArrayListService = makeArrayListService;
	}
	public UserServiceImpl(){}
	public User loadUserByUsername(Long uid) throws RuntimeException {
		log.info("UserService.loadUserByUsername:::");
		try {
			return Optional.of(userDAO.selectOne(uid)).orElseThrow(NoInformationException::new);
		}
		catch(Exception e){
			log.info(e.toString());
			throw new NoInformationException();
		}

	}
	@Override
	public ArrayList<UserInfo> mergeUserInfoArraylist(ArrayList<UserInfo> userInfoArrayList,List<User> userInfoList,User chief){
		userInfoArrayList.addAll(sortwithUserName(makeArrayListService.addParticipantsToArrayList(userInfoList,chief)));
		return userInfoArrayList;
	}
	@Override
	public ArrayList<UserInfo> sendUserInfo(String familyId) throws RuntimeException {
		Long chiefId = Optional.of(codeUserDAO.getUidFromCode(familyId)).orElseThrow(NoInformationException::new);
		User chief = this.loadUserByUsername(chiefId);
		List<User> userInfoList = userDAO.selectfamilyId(familyId);
		ArrayList<UserInfo> userInfoArrayList= makeArrayListService.userInfoToArrayList(chief,userInfoList);
		return mergeUserInfoArraylist(userInfoArrayList,userInfoList,chief);
	}

	@Override
	public ArrayList<UserInfo> sortwithUserName(ArrayList<UserInfo> userInfoArrayList){
		userInfoArrayList.sort((arg0,arg1)->{
			String name0 = arg0.getName();
			String name1 = arg1.getName();
			log.info(name0 + name1);
			return this.sortName(name0,name1);
		});
		return userInfoArrayList;
	}
	@Override
	public int sortName(String name0,String name1){
		if(name0.equals(name1)) return 0;
		int i = 0;
		for(char ch : name0.toCharArray()) {
			if(ch == name1.charAt(i)) i++;
			else if (ch > name1.charAt(i)) return 1;
			else return -1;
		}
		return -1;
	}

	@Transactional
	@Override
	public HashMap<String,String> changeColorAndEtc(FamilyInfo familyInfo) throws RuntimeException{
		HashMap<String,String> map = new HashMap<>();
		String familyId = familyInfo.getCode();
		this.updateUser(this.setUserColorAndEtc(familyInfo.getUid(),familyId,familyInfo.getColor(),familyInfo.getRelationship()));
		map.put("familyId",familyId);
		return map;
	}
	@Override
	public void updateUser(User user) throws RuntimeException{
		try {
			userDAO.update(user);
		}catch(Exception e){
			throw new NoStoringException();
		}
	}

	@Override
	public User setUserColorAndEtc(long uid,String familyId,String color,String relationship){
		User user = Optional.of(this.loadUserByUsername(uid)).orElseThrow(NoInformationException::new);
		user.setFamilyId(familyId);
		user.setColor(color);
		user.setRelationship(relationship);
		return user;
	}

}