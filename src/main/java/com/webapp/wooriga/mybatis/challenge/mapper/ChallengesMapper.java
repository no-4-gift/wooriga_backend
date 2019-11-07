package com.webapp.wooriga.mybatis.challenge.mapper;

import com.webapp.wooriga.mybatis.vo.Challenges;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChallengesMapper {
    Challenges selectChallenge(@Param("challengeId") long challengeId);
    void insertChallenge(Challenges challenges);
    void updateChallenge(Challenges challenges);
}
