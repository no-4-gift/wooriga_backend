package com.webapp.wooriga.mybatis.calendar.result;

import com.webapp.wooriga.mybatis.challenge.result.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@Builder
public class EmptyDayUserInfo {
    private String emptyDate;
    private UserInfo userInfo;

}
