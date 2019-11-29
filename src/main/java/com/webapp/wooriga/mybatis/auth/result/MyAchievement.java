package com.webapp.wooriga.mybatis.auth.result;

import com.webapp.wooriga.mybatis.challenge.result.ChallengeBarInfo;
import com.webapp.wooriga.mybatis.challenge.result.ChallengeViewInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class MyAchievement {
    private ChallengeViewInfo challengeViewInfo;
    private Boolean successTrue;

    public MyAchievement(Boolean successTrue,ChallengeViewInfo challengeViewInfo){
        this.challengeViewInfo = challengeViewInfo;
        this.successTrue = successTrue;
    }
    public MyAchievement(){}
}
