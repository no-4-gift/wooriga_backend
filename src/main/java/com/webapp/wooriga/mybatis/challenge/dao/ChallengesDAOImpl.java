package com.webapp.wooriga.mybatis.challenge.dao;

import com.webapp.wooriga.mybatis.challenge.mapper.ChallengesMapper;
import com.webapp.wooriga.mybatis.vo.Challenges;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChallengesDAOImpl implements ChallengesDAO {
    @Autowired
    private ChallengesMapper challengesMapper;

    @Override
    public Challenges selectChallenge(@Param("challengeId")long challengeId){
        return challengesMapper.selectChallenge(challengeId);
    }
    @Override
    public void insertChallenge(Challenges challenges){
        challengesMapper.insertChallenge(challenges);
    }
    @Override
    public void updateChallenge(Challenges challenges){
        challengesMapper.updateChallenge(challenges);
    }

    @Override
    public List<Challenges> selectChallengeList(){
        return challengesMapper.selectChallengeList();
    }
}
