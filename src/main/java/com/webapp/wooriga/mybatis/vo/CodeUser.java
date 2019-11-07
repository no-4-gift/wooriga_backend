package com.webapp.wooriga.mybatis.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeUser {
    private long uid;
    private String code;

    public CodeUser(long uid, String code) {
        this.uid = uid;
        this.code = code;
    }
}
