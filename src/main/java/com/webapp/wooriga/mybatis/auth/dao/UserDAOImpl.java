package com.webapp.wooriga.mybatis.auth.dao;

import java.util.List;

import com.webapp.wooriga.mybatis.auth.mapper.UserMapper;
import com.webapp.wooriga.mybatis.vo.CodeUser;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import com.webapp.wooriga.mybatis.vo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private UserMapper mapper;
	
	@Override
	public List<User> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public User selectOne(long uid) {
		return mapper.selectOne(uid);
	}

	@Override
	public void insert(User user) {
		mapper.insert(user);
	}

	@Override
	public void delete(User user) {
		mapper.delete(user);
	}

	@Override
	public void update(User user) {
		mapper.update(user);
	}

	@Override
	public int checkUser(long uid) {
		return mapper.checkUser(uid);
	}

	@Override
	public String getCode(long uid) {
		return mapper.getCode(uid);
	}

	@Override
	public void insertCodeUser(CodeUser codeuser) {
		mapper.insertCodeUser(codeuser);
	}

	@Override
	public User selectUserForCalendar(EmptyDays emptyDays) { return mapper.selectUserForCalendar(emptyDays);}

	@Override
	public int selectUserToFamilyId(@Param("familyId")String familyId){ return mapper.selectUserToFamilyId(familyId);}
}
