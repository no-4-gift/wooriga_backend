package com.webapp.wooriga.mybatis.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//409
@ResponseStatus(HttpStatus.CONFLICT)
public class NoStoringException extends RuntimeException{
    public NoStoringException(){ super();}

}
