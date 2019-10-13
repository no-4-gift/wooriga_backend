package com.webapp.wooriga.mybatis.vo;

import lombok.Getter;
import lombok.Setter;
import springfox.documentation.spring.web.json.Json;

@Getter
@Setter
public class RegisteredChallenges {
    private long registeredId;
    private String chiefIdFK;
    private Json participantsIdFK;
    private long challengedIdFK;
    private Json registeredDate;
    private String resolution;
    public RegisteredChallenges(long registeredId,String chiefIdFK,Json participantsIdFK,long challengedIdFK,Json registeredDate,String resolution){
        this.registeredId = registeredId;
        this.chiefIdFK = chiefIdFK;
        this.participantsIdFK = participantsIdFK;
        this.challengedIdFK = challengedIdFK;
        this.registeredDate = registeredDate;
        this.resolution = resolution;
    }
}
