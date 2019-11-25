package com.webapp.wooriga.mybatis.challenge.mapper;

import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RegisteredChallengesMapper {
    void insertRegisteredChallenge(RegisteredChallenges registeredChallenges);
    RegisteredChallenges selectRegisteredChallenge(@Param("registeredId") long registeredId);
}
