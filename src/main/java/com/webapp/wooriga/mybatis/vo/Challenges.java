package com.webapp.wooriga.mybatis.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Challenges {
    private long challengeId;
    private String title;
    private String summary;
    private String content;
    private String image;

    public Challenges(String image,long challengeId, String title,String summary, String content){
        this.challengeId = challengeId;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.image = image;
    }
    public Challenges(){}
}
