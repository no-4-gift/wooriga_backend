package com.webapp.wooriga.mybatis.challenge.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChallengeViewInfo {
    private ChallengeBarInfo challengeBarInfo;
    private String challengeImage;
}
