package com.webapp.wooriga.mybatis.challenge.result;

import lombok.Data;

@Data
public class ParticipantsInfo {
    private long participantId;
    private String participantImage;
    private String participantName;
    private String participantColor;
    private String relationship;

    public ParticipantsInfo(String relationship,String participantColor,long participantId, String participantImage, String participantName){
        this.participantId = participantId;
        this.relationship = relationship;
        this.participantImage = participantImage;
        this.participantName = participantName;
        this.participantColor = participantColor;
    }
}
