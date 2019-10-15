package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.webapp.wooriga.mybatis.controller.deserializer.RegisteredInformationDeserializer;

@JsonDeserialize(using= RegisteredInformationDeserializer.class)
public class RegisteredInformation {
    public Participants[] participants;
    public RegisteredDates[] registeredDates;
    public RegisteredChallenges registeredChallenges;
    public RegisteredInformation(){}

    public void setParticipants(Participants[] participants) {
        this.participants = participants;
    }
    public void setRegisteredDates(RegisteredDates[] registeredDates){
        this.registeredDates = registeredDates;
    }
    public void setRegisteredChallenges(RegisteredChallenges registeredChallenges){
        this.registeredChallenges = registeredChallenges;
    }
    public Participants[] getParticipants(){
        return participants;
    }
    public RegisteredDates[] getRegisteredDates(){
        return registeredDates;
    }
    public RegisteredChallenges getRegisteredChallenges(){
        return registeredChallenges;
    }
}
