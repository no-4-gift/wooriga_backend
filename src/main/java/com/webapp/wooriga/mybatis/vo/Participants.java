package com.webapp.wooriga.mybatis.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Participants {
    private long registeredIdFK;
    private long participantFK;
}
