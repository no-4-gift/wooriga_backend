package com.webapp.wooriga.mybatis.challenge.result;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.webapp.wooriga.mybatis.challenge.deserializer.RegisteredInformationDeserializer;
import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.vo.Participants;
import com.webapp.wooriga.mybatis.vo.RegisteredChallenges;
import lombok.Getter;
import lombok.Setter;

@JsonDeserialize(using= RegisteredInformationDeserializer.class)
@Getter
@Setter
public class RegisteredInformation {
    public Participants[] participants;
    public Certifications[] certifications;
    public RegisteredChallenges registeredChallenges;
    public RegisteredInformation(){}

}
