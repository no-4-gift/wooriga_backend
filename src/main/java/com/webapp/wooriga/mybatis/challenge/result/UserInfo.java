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
    private String relationship;
    public UserInfo(String relationship,String name,String profile,String color, long uid){
        this.relationship = relationship;
        this.profile = profile;
        this.name = name;
        this.color = color;
        this.uid = uid;
    }
}
