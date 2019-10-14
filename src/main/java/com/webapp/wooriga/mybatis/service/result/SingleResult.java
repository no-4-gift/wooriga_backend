package com.webapp.wooriga.mybatis.service.result;


public class SingleResult<T> extends CommonResult {
    private T data;
    public SingleResult(){
        super();
        data = null;
    }
    public T getData() {
        return data;
    }
    public void setData(T data){
        this.data = data;
    }
    public void setFailResult( int code, String msg, T data){
        super.setSuccess(false);
        super.setSuccessResult(code,msg);
        this.data = data;
    }
    public void setSuccessResult(int code, String msg,T data){
        super.setSuccess(true);
        super.setSuccessResult(code,msg);
        this.data = data;
    }
}