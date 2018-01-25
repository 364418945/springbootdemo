package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@ResponseBody
public class HelloController {

    @Value("${abc}")
    String param;

    @Value("${content}")
    String content;

    @Autowired
    GirlProperties girlProperties;

    @RequestMapping(value = "/abc/hello",method = RequestMethod.GET)
    public ModelAndView hello(){
        ModelAndView mv = new ModelAndView("redirect:/hello");//redirect模式
        return mv;
    }

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello1(){

        System.out.println(3333 );
        return content+"789";
    }

    @RequestMapping(value = "/hello2",method = RequestMethod.GET)
    public String hello2(){
        return girlProperties.getAge()+girlProperties.getCc();
    }

    @RequestMapping(value = "/hello3",method = RequestMethod.GET)
    @ResponseBody//如果类上是@Controller需要添加，如果是@RestController不需要添加
    public GirlProperties hello3(){
        return girlProperties;
    }

    //http://localhost:8081/ss/hello4/2
    @RequestMapping(value = "/hello4/{id}",method = RequestMethod.GET)
    public Integer hello4(@PathVariable(value = "id") Integer id){
        return id;
    }

    //http://localhost:8081/ss/hello5?id=1
    @RequestMapping(value = "/hello5",method = RequestMethod.GET)
    //@GetMapping(value = "/hello5")等同value+method
    public String hello5(@RequestParam(value = "id") String id){
        return id;
    }



}
