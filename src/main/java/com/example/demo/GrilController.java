package com.example.demo;

import com.example.demo.handle.ExceptionEnum;
import com.example.demo.handle.GrilException;
import com.example.demo.model.dto.Gril;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class GrilController {

    @Autowired
    GirlRespository girlRespository;

    @GetMapping(value = "/girl/{id}")
    public Gril hello(@PathVariable(value = "id") Integer idd){
        log.info("【hello】 idd {} ",idd);
        Gril gril =  girlRespository.getOne(idd);
        System.out.println(gril.toString());
        return gril;
    }


    @PostMapping(value = "/girl")
    public void helloAdd(@RequestParam(value="age") Integer age,
                         @RequestParam(value="sex",required = false) String sex,
                         @RequestParam(value="cupSize") Integer cupSize,
                         @RequestParam(value="hobby") String hobby){
//        log.info("【helloAdd】 age {} sex {} cupSize {} hobby {}",age,sex,cupSize,hobby);
        Gril gril = new Gril();
        gril.setAge(age);
        gril.setSex(sex);
        gril.setCupSize(cupSize);
        gril.setHobby(hobby);
        Gril gril1 =  girlRespository.save(gril);
    }

    /**
     * 设置验证
     * @param girl
     * @param bindingResult
     */
    @PostMapping(value = "/girl1")
    public Result<Gril> helloAdd1(@Valid Gril girl, BindingResult bindingResult)throws Exception{
        Result result = new Result();
//        if(bindingResult.hasErrors()){
            if(girl.getAge()>30){
                throw new GrilException(ExceptionEnum.LESS30);
            }else if(girl.getAge()>40){
                throw new GrilException(ExceptionEnum.LESS40);
            }
//        }
        result.setCode(0);
        result.setMessage("成功");
        Gril gril1 =  girlRespository.save(girl);
        result.setData(gril1);
        return result;
    }




    /**
     * 拦截器aop
     * @param girl
     * @param bindingResult
     */
    @PostMapping(value = "/girl2")
    public void helloAdd2(@Valid Gril girl, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【helloAdd2】获取参数错误 {}",bindingResult.getFieldError().getDefaultMessage());
            return;
        }
        Gril gril1 =  girlRespository.save(girl);
        System.out.println(gril1.toString());
    }


    /**
     * 用于生成测试文件 右键单击 goto
     */
    public Gril getGril(Integer id){
        return girlRespository.findOne(id);
    }
}
