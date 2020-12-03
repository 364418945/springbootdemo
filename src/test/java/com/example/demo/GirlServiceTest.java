package com.example.demo;

import com.example.demo.model.dto.Gril;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * service测试
 */
@RunWith(SpringRunner.class)//测试环境跑
@SpringBootTest//启动spring工程
public class GirlServiceTest {
    @Autowired
    GirlRespository girlRespository;

//    @Test
//    public void test(){
//        Gril gril = girlRespository.findOne(1);
//        Assert.assertEquals(1,(long)gril.getAge());
//        int i = '5';//获取5的asiII码
//        char b = 98;//获取asiII码为98的字符
//        System.out.println(i);
//        System.out.println(b);
//    }
}
