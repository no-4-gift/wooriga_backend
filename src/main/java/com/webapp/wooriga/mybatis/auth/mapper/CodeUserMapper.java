package com.webapp.wooriga.mybatis.auth.mapper;

import com.webapp.wooriga.mybatis.vo.CodeUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CodeUserMapper {
    long getUid(@Param("code")String code);

    String getCode(@Param("uid")long uid);

    void insertCodeUser(CodeUser codeuser);

    Long getUidFromCode(@Param("code")String code);

    int checkUser(@Param("uid")long uid);

    void updateChief(CodeUser codeUser);
}
