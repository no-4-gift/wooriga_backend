package com.webapp.wooriga.mybatis.vo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Challenges {
    private int challengeId;
    private String title;
    private String summary;
    private String content;
    private ChallengeImages challengeImages;
}
