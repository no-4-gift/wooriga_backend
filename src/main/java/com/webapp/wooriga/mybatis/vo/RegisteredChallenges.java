package com.webapp.wooriga.mybatis.vo;

import lombok.Getter;
import lombok.Setter;
import springfox.documentation.spring.web.json.Json;

@Getter
@Setter
public class RegisteredChallenges {
    private long registeredId;
    private String chiefIdFK;
    private String participantsIdFK;
    private long challengeIdFK;
    private String registeredDate;
    private String resolution;

    public RegisteredChallenges(long registeredId,String chiefIdFK,String participantsIdFK,long challengeIdFK,String registeredDate,String resolution){
        this.registeredId = registeredId;
        this.chiefIdFK = chiefIdFK;
        this.participantsIdFK = participantsIdFK;
        this.challengeIdFK = challengeIdFK;
        this.registeredDate = registeredDate;
        this.resolution = resolution;
    }
}
