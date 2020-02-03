package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Participants {
    private long registeredIdFK;
    private long participantFK;
}
