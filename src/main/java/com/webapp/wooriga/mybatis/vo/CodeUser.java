package com.webapp.wooriga.mybatis.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CodeUser {
    private long uid;
    private String code;
}
