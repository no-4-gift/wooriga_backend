package com.webapp.wooriga.mybatis.challenge.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String profile;
    private String color;
    private long uid;
    private String name;
    public UserInfo(String name,String profile,String color, long uid){
        this.profile = profile;
        this.name = name;
        this.color = color;
        this.uid = uid;
    }
}
