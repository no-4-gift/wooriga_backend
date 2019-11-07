package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisteredChallenges {
    private long registeredId;
    private long chiefIdFK;
    private String familyIdFK;
    private long challengeIdFK;
    private String resolution;

    public RegisteredChallenges(long registeredId,long chiefIdFK, long challengeIdFK,String resolution,String familyIdFK){
        this.registeredId = registeredId;
        this.chiefIdFK = chiefIdFK;
        this.familyIdFK = familyIdFK;
        this.challengeIdFK = challengeIdFK;
        this.resolution = resolution;
    }
    public RegisteredChallenges(){ }
}
