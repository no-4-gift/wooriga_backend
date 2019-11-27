package com.webapp.wooriga.mybatis.challenge.dao;

import com.webapp.wooriga.mybatis.challenge.mapper.ChallengeImagesMapper;
import com.webapp.wooriga.mybatis.vo.ChallengeImages;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChallengeImagesDAOImpl implements ChallengeImagesDAO{
    private ChallengeImagesMapper challengeImagesMapper;

    @Autowired
    public ChallengeImagesDAOImpl(ChallengeImagesMapper challengeImagesMapper){
        this.challengeImagesMapper = challengeImagesMapper;
    }
    @Override
    public ChallengeImages selectChallengeId(@Param("challengeId")int challengeId, @Param("pageId")int pageId){
        return challengeImagesMapper.selectChallengeId(challengeId,pageId);
    }
}
