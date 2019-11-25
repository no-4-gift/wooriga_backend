package com.webapp.wooriga.mybatis.calendar.result;

import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class EmptyDayUserInfo {
    private String emptyDate;
    private UserInfo userInfo;

    public EmptyDayUserInfo(UserInfo userInfo,String emptyDate){
        this.emptyDate = emptyDate;
        this.userInfo = userInfo;

    }
    public EmptyDayUserInfo(){

    }

}
