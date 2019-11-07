package com.webapp.wooriga.mybatis.challenge.mapper;

import com.webapp.wooriga.mybatis.vo.Participants;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ParticipantsMapper {
    void insertParticipants(Participants participants);
}
