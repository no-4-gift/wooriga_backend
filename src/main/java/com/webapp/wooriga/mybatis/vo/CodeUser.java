package com.webapp.wooriga.mybatis.vo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeUser {
    private long uid;
    private String code;
}
