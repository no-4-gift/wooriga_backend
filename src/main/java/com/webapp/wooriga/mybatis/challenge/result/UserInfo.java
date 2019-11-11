package com.webapp.wooriga.mybatis.challenge.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String profile;
    private String color;
    private long uid;
    public UserInfo(String profile,String color, long uid){
        this.profile = profile;
        this.color = color;
        this.uid = uid;
    }
}
