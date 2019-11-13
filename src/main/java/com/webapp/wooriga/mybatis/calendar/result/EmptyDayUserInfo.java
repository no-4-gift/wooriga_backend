package com.webapp.wooriga.mybatis.calendar.result;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class EmptyDayUserInfo {
    private String emptyDate;
    private String color;
    private String name;
    private String relationship;
    private String profile;


    public EmptyDayUserInfo(String emptyDate, String color, String name, String relationship, String profile){
        this.emptyDate = emptyDate;
        this.color = color;
        this.name = name;
        this.relationship = relationship;
        this.profile = profile;
    }
    public EmptyDayUserInfo(){

    }

}