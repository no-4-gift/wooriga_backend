package com.webapp.wooriga.mybatis.challenge.mapper;

import com.webapp.wooriga.mybatis.vo.ChallengeImages;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChallengeImagesMapper {

    ChallengeImages selectChallengeId(@Param("challengeId")int challengeId, @Param("pageId")int pageId);
}
