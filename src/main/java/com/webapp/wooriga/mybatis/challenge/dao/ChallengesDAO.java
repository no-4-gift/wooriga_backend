package com.webapp.wooriga.mybatis.challenge.dao;


import com.webapp.wooriga.mybatis.vo.Challenges;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface ChallengesDAO {
    Challenges selectChallenge(@Param("challengeId")long challengeId);
    void insertChallenge(Challenges challenges);
    void updateChallenge(Challenges challenges);
    List<Challenges> selectChallengeList();
}
