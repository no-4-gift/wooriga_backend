package com.webapp.wooriga.mybatis.dao;

import com.webapp.wooriga.mybatis.mapper.RegisteredChallengesMapper;
import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegisteredChallengesDAOImpl implements RegisteredChallengesDAO {
    private RegisteredChallengesMapper registeredChallengesMapper;
    @Autowired
    public RegisteredChallengesDAOImpl(RegisteredChallengesMapper registeredChallengesMapper){
        this.registeredChallengesMapper = registeredChallengesMapper;
    }

    @Override
    public void insertRegisteredChallenge(RegisteredChallenges registeredChallenges){
        registeredChallengesMapper.insertRegisteredChallenge(registeredChallenges);
    }
}
