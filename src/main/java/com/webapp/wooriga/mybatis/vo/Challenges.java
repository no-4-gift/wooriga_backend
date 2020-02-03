package com.webapp.wooriga.mybatis.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Challenges {
    private int challengeId;
    private String title;
    private String summary;
    private String content;
    private ChallengeImages challengeImages;

}
