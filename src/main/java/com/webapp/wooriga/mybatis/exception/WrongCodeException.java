package com.webapp.wooriga.mybatis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//422
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class WrongCodeException extends RuntimeException{
    public WrongCodeException(){super();}
}
