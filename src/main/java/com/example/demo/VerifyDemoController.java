package com.example.demo;

import com.example.demo.model.ParamVerify;
import com.example.demo.model.dto.ObjectionResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class VerifyDemoController {

//    @GetMapping(value="/verifydemo")
//    @ParamVerify(value = {ObjectionResource.class})
//    public void verifyDemo(HttpServletRequest request,ObjectionResource objectionResource) throws Exception {
//        log.info("执行验证用例");
//    }
}
