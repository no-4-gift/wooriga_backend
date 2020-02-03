package com.webapp.wooriga.mybatis.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisteredChallenges {

    private long registeredId;
    private long chiefIdFK;
    private String familyId;
    private int challengeIdFK;
    private String resolution;
}
