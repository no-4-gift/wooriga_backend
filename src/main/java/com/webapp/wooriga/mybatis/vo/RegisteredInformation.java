package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.webapp.wooriga.mybatis.challenge.deserializer.RegisteredInformationDeserializer;

@JsonDeserialize(using= RegisteredInformationDeserializer.class)
public class RegisteredInformation {
    public Participants[] participants;
    public Certifications[] certifications;
    public RegisteredChallenges registeredChallenges;
    public RegisteredInformation(){}

    public void setParticipants(Participants[] participants) {
        this.participants = participants;
    }
    public void setCertifications(Certifications[] certifications){
        this.certifications = certifications;
    }
    public void setRegisteredChallenges(RegisteredChallenges registeredChallenges){
        this.registeredChallenges = registeredChallenges;
    }
    public Participants[] getParticipants(){
        return participants;
    }
    public Certifications[] getCertifications(){
        return certifications;
    }
    public RegisteredChallenges getRegisteredChallenges(){
        return registeredChallenges;
    }
}
