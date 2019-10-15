package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Participants {
    private long registeredIdFK;
    private String participantFK;
    public Participants(){

    }


}
