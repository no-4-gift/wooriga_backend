package com.webapp.wooriga.mybatis.auth.service;

import com.webapp.wooriga.mybatis.auth.result.MyRecordInfo;

public interface MyPageService {
    MyRecordInfo sendMyRecordInfo(String familyId, long uid) throws RuntimeException;
}
