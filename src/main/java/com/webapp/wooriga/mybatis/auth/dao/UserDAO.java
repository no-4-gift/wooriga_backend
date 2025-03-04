package com.webapp.wooriga.mybatis.auth.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.webapp.wooriga.mybatis.vo.CodeUser;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import com.webapp.wooriga.mybatis.vo.User;
import org.apache.ibatis.annotations.Param;

public interface UserDAO {
	public List<User> selectAll();
	public User selectOne(Long uid);
	public void insert(User user);
	public void delete(User user);
	public void update(User user);
	public void updateMyInfo(User user);
	public void updateFamilyId(User user);
	public List<User> familyAll(String family_id);
	public String checkFamilyId(long uid);
	User selectUserForCalendar(EmptyDays emptyDays);
	int selectUserToFamilyId(HashMap<String,Object> familyMap);
	List<User> selectUserId(HashMap<String,Object> userMap);
	List<User> selectfamilyId(@Param("familyId")String familyId);
}