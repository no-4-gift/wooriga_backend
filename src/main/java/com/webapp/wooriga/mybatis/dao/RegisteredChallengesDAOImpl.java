package com.webapp.wooriga.mybatis.dao;

import com.webapp.wooriga.mybatis.mapper.RegisteredChallengesMapper;
import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegisteredChallengesDAOImpl {
    @Autowired
    SqlSessionTemplate mybatis;

    public void insertRegisteredChallenge(RegisteredChallenges registeredChallenges){
        RegisteredChallengesMapper registeredChallengesMapper = mybatis.getMapper(RegisteredChallengesMapper.class);
        registeredChallengesMapper.insertRegisteredChallenge(registeredChallenges);
    }
}
