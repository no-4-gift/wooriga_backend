package com.webapp.wooriga.mybatis.challenge.dao;

import com.webapp.wooriga.mybatis.vo.Participants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParticipantsDAO {
    void insertParticipants(Participants participants);
    List<Participants> selectParticipants(@Param("registeredId") long registeredId);
    int selectUserIsCorrectParticipant(@Param("registeredId") long registeredId,@Param("uid")long uid);
}
