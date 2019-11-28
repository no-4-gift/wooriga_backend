package com.webapp.wooriga.mybatis.auth.mapper;

import java.util.HashMap;
import java.util.List;

import com.webapp.wooriga.mybatis.vo.CodeUser;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import com.webapp.wooriga.mybatis.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

 	List<User> selectAll();

 	User selectOne(long uid);

 	void insert(User user);

	void delete(User user);

	void update(User user);

	void updateFamilyId(User user);

	List<User> familyAll(String family_id);

	String checkFamilyId(long uid);

	User selectUserForCalendar(EmptyDays emptyDays);

	int selectUserToFamilyId(HashMap<String, Object> familyMap);

	List<User> selectUserId(HashMap<String, Object> userMap);

	List<User> selectfamilyId(@Param("familyId") String familyId);


}