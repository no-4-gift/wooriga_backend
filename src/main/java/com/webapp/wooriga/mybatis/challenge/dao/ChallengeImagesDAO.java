package com.webapp.wooriga.mybatis.challenge.dao;

import com.webapp.wooriga.mybatis.vo.ChallengeImages;
import org.apache.ibatis.annotations.Param;

public interface ChallengeImagesDAO {
    ChallengeImages selectChallengeId(@Param("challengeId")int challengeId, @Param("pageId")int pageId);
}
