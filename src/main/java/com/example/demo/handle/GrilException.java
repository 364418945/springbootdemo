package com.example.demo.handle;

public class GrilException extends RuntimeException {

    private Integer code;

    public GrilException(){

    }

    public GrilException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
