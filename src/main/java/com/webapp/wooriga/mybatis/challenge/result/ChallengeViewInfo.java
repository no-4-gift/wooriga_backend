package com.webapp.wooriga.mybatis.challenge.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeViewInfo {
    private ChallengeBarInfo challengeBarInfo;
    private String challengeImage;

    public ChallengeViewInfo(){}
    public ChallengeViewInfo(ChallengeBarInfo challengeBarInfo,String challengeImage){
        this.challengeImage = challengeImage;
        this.challengeBarInfo = challengeBarInfo;
    }
}
