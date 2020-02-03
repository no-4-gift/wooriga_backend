package com.webapp.wooriga.mybatis.challenge.result;

import com.webapp.wooriga.mybatis.vo.Participants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@Builder
public class ChallengeBarInfo {
    private ArrayList<UserInfo> userInfo;
    private ArrayList<String> date;
    private long registeredId;
    private String resolution;
    private int challengeId;
    private String challengeTitle;
    private int totalNum;
    private int certificationNum;
    private int percentage;
    private String viewDate;
}
