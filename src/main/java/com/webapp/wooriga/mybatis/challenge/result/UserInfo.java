package com.webapp.wooriga.mybatis.challenge.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfo {
    private String profile;
    private String color;
    private Long uid;
    private String name;
    private String relationship;

}
