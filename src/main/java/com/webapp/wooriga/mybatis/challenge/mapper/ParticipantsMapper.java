package com.webapp.wooriga.mybatis.challenge.mapper;

import com.webapp.wooriga.mybatis.vo.Participants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ParticipantsMapper {
    void insertParticipants(Participants participants);
    List<Participants> selectParticipants(@Param("registeredId") long registeredId);
}
