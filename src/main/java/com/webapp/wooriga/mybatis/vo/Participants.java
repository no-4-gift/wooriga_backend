package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Participants {
    private long registeredIdFK;
    private long participantFK;
    public Participants(long registeredIdFK, long participantFK){
        this.registeredIdFK = registeredIdFK;
        this.participantFK = participantFK;
    }
    public Participants(){

    }


}
