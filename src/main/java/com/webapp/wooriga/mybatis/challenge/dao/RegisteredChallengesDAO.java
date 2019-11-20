package com.webapp.wooriga.mybatis.challenge.dao;

import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;
import org.apache.ibatis.annotations.Param;

public interface RegisteredChallengesDAO {
   void insertRegisteredChallenge(RegisteredChallenges registeredChallenges);
   RegisteredChallenges selectRegisteredChallenge(@Param("registeredId") long registeredId);
}
