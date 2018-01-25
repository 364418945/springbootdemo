package com.example.demo.handle;

import com.example.demo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常统一处理
 */
@ControllerAdvice
public class ExceptionHandlera {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result handle(Exception e){
        Result result = new Result();
        if(e instanceof GrilException){
            GrilException grilException = (GrilException)e;
            result.setCode(grilException.getCode());
            result.setMessage(grilException.getMessage());
        }else if(e instanceof IllegalArgumentException){
            result.setCode(400);
            result.setMessage(e.getMessage());
        }else{
            result.setCode(-1);
            result.setMessage("未知错误");
        }

        return result;
    }
}
