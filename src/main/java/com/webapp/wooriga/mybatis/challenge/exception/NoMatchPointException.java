package com.webapp.wooriga.mybatis.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//411
@ResponseStatus(HttpStatus.LENGTH_REQUIRED)
public class NoMatchPointException extends RuntimeException {
    public NoMatchPointException(){super();}

}
