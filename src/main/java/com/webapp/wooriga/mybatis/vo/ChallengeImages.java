package com.webapp.wooriga.mybatis.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeImages {

    private String image;

    public ChallengeImages(){}
    public ChallengeImages(String image){
        this.image = image;
    }
}
