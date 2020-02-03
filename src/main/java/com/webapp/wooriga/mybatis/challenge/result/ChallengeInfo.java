package com.webapp.wooriga.mybatis.challenge.result;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.webapp.wooriga.mybatis.vo.Challenges;
import com.webapp.wooriga.mybatis.vo.EmptyDays;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@Builder
public class ChallengeInfo {
    public ArrayList<UserInfo> userInfo;
    public ArrayList<Challenges> challenges;
}
