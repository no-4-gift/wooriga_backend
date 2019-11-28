package com.webapp.wooriga.mybatis.auth.service;

import java.lang.reflect.Array;
import java.util.*;

import com.webapp.wooriga.mybatis.auth.dao.CodeUserDAO;
import com.webapp.wooriga.mybatis.auth.dao.UserDAO;
import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import com.webapp.wooriga.mybatis.exception.NoInformationException;
import com.webapp.wooriga.mybatis.vo.CodeUser;
import com.webapp.wooriga.mybatis.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component("service")
public class UserServiceImpl implements UserService {

	private UserDAO userDAO;
	private CodeUserDAO codeUserDAO;
	@Autowired
	public UserServiceImpl(UserDAO userDAO,CodeUserDAO codeUserDAO){
		this.userDAO = userDAO;
		this.codeUserDAO = codeUserDAO;
	}
	public UserServiceImpl(){}

	@Override
	public ArrayList<UserInfo> sendUserInfo(String familyId) throws RuntimeException {
		Long chiefId = codeUserDAO.getUidFromCode(familyId);
		if(chiefId == null) throw new NoInformationException();
		User chief = userDAO.selectOne(chiefId);
		List<User> userInfoList = userDAO.selectfamilyId(familyId);
		return addUserInfoToArrayList(chief,userInfoList);
	}
	@Override
	public ArrayList<UserInfo> addUserInfoToArrayList(User chief,List<User> userInfoList) throws RuntimeException{
		if(userInfoList.size() == 0) throw new NoInformationException();

		ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();
		userInfoArrayList = convertUserToUserInfoAndAddArrayList(chief,userInfoArrayList);
		ArrayList<UserInfo> participantInfoArrayList = new ArrayList<>();
		for(User user : userInfoList) {
			if(user.getUid() == chief.getUid()) continue;
			participantInfoArrayList = convertUserToUserInfoAndAddArrayList(user,participantInfoArrayList);
		}
		userInfoArrayList.addAll(sortwithUserName(participantInfoArrayList));
		return userInfoArrayList;
	}
	private ArrayList<UserInfo> convertUserToUserInfoAndAddArrayList(User user,ArrayList<UserInfo> userInfoArrayList){
		UserInfo userInfo = new UserInfo(user.getRelationship(), user.getName(), user.getProfile(), user.getColor(), user.getUid());
		userInfoArrayList.add(userInfo);
		return userInfoArrayList;
	}
	private ArrayList<UserInfo> sortwithUserName(ArrayList<UserInfo> userInfoArrayList){
		userInfoArrayList.sort((arg0,arg1)->{
			String name0 = arg0.getName();
			String name1 = arg1.getName();
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

	@Override
	public Map admin(User user, String accessToken) throws RuntimeException {

		HashMap<String, String> map = new HashMap<String, String>();

		try {
			// 랜덤코드 테이블에서 현재 관리자 코드 존재 여부 확인
			int check = codeUserDAO.checkUser(user.getUid());
			String getCode = "";

			System.out.println("check : " + check);
			// 없으면 8자리 생성
			if (check != 1) {
				Random rnd = new Random();
				StringBuffer buf = new StringBuffer();

				for (int i = 0; i < 8; i++) {
					if (rnd.nextBoolean()) {
						buf.append((char) ((int) (rnd.nextInt(26)) + 65));
					} else {
						buf.append((rnd.nextInt(10)));
					}
				}

				String code = buf.toString();

				codeUserDAO.insertCodeUser(new CodeUser(user.getUid(), code)); // codeUser에 저장
				user.setFamilyId(code);
				userDAO.updateFamilyId(user); // family_id 갱신
				System.out.println(user.getUid() + "님의 생성 코드 : " + code);
				getCode = code;
				map.put("code", getCode);
			} else {
				getCode = codeUserDAO.getCode(user.getUid());
				map.put("code", getCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

}