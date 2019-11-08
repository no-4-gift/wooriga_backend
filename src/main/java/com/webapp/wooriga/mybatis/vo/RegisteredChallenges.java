package com.webapp.wooriga.mybatis.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisteredChallenges {
    private long registeredId;
    private long chiefIdFK;
    private String familyId;
    private long challengeIdFK;
    private String resolution;

    public RegisteredChallenges(long registeredId,long chiefIdFK, long challengeIdFK,String resolution,String familyId){
        this.registeredId = registeredId;
        this.chiefIdFK = chiefIdFK;
        this.familyId = familyId;
        this.challengeIdFK = challengeIdFK;
        this.resolution = resolution;
    }
    public RegisteredChallenges(){ }
}
