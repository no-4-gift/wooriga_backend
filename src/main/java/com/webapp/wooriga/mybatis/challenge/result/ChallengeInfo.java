package com.webapp.wooriga.mybatis.challenge.result;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.webapp.wooriga.mybatis.vo.Challenges;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ChallengeInfo {
    public ArrayList<EmptyDays> emptyDays;
    public ArrayList<Challenges> challenges;

    public ChallengeInfo(){
    }
}
