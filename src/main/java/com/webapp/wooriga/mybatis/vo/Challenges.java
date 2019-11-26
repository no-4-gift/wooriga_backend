package com.webapp.wooriga.mybatis.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Challenges {
    private int challengeId;
    private String title;
    private String summary;
    private String content;
    private ChallengeImages challengeImages;

    public Challenges(ChallengeImages challengeImages,int challengeId, String title,String summary, String content){
        this.challengeId = challengeId;
        this.challengeImages = challengeImages;
        this.title = title;
        this.summary = summary;
        this.content = content;
    }
    public Challenges(){}
}
