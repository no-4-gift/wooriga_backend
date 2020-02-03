package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredChallenges {
    private long registeredId;
    private long chiefIdFK;
    private String familyId;
    private int challengeIdFK;
    private String resolution;
}
