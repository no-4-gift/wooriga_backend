package com.webapp.wooriga.mybatis.challenge.result;

import com.webapp.wooriga.mybatis.vo.Certifications;
import com.webapp.wooriga.mybatis.vo.Challenges;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ChallengeDetailInfo {
    private ArrayList<Certifications> certificationsArrayList;
    private ArrayList<Challenges> challengesArrayList;

    
}
