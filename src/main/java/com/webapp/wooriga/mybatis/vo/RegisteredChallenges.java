package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisteredChallenges {
    private long registeredId;
    private String chiefIdFK;
    private long familyIdFK;
    private long challengeIdFK;
    private String resolution;

    public RegisteredChallenges(long registeredId,String chiefIdFK, long challengeIdFK,String resolution,long familyIdFK){
        this.registeredId = registeredId;
        this.chiefIdFK = chiefIdFK;
        this.familyIdFK = familyIdFK;
        this.challengeIdFK = challengeIdFK;
        this.resolution = resolution;
    }
    public RegisteredChallenges(){ }
}
