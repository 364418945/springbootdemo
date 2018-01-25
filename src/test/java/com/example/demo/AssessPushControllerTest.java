package com.example.demo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.JsonPath;
import common.PostMan_v2;
import common.ServerTestCommon;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootdemoApplication.class)
@AutoConfigureMockMvc//模拟
@ActiveProfiles("test")
@ConfigurationProperties(prefix = "server")
public class AssessPushControllerTest extends ServerTestCommon {
    @Autowired
    MockMvc mockMvc;

    PostMan_v2 postMan;

    @Value("classpath:${filePath}")
    private Resource resource;

    @Before
    public void setUp() {
        postMan = new PostMan_v2(mockMvc,resource,new ServerTestCommon(getApiKey(),getUserToken(),getIp_port()));
    }

    @Test
    public void priceAlert() throws Exception {
        postMan.doMissArgument(ImmutableList.of(
                "6.1 定制房价预警(无suitCode)",
                "6.1 定制房价预警(无op)",
                "6.1 定制房价预警(无minPrice)",
                "6.1 定制房价预警(无PriceType)",
                "6.1 定制房价预警(无alertType)",
                "6.1 定制房价预警(无uuid)"
        ));

        //6.1 定制房价预警(添加成功)
        //先添加房产
        ResultActions resultActions = postMan.getExampleRequest("2.2添加房产(成功)").doExampleRequest()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.suitcode").exists());
        String suitCode = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.suitcode");
        //更新为以加入管理
        postMan.getExampleRequest("2.4更新房产(更新全部成功)").replaceUrlKeyValue(ImmutableMap.of("suitcode", suitCode)).doExampleRequest()
                .andExpect(MockMvcResultMatchers.status().isOk());
        //成功
        postMan.getExampleRequest("6.1 定制房价预警(添加成功)").replaceUrlKeyValue(ImmutableMap.of("suitCode", suitCode)).doExampleRequest()
                .andExpect(MockMvcResultMatchers.status().isOk());
        //查看
        //6.2 查看房价预警(成功)
        resultActions = postMan.getExampleRequest("6.2 查看房价预警(成功)").replaceUrlKeyValue(ImmutableMap.of("suitCode", suitCode)).doExampleRequest()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceAlerts").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceAlerts[?(@.uuid)]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceAlerts[?(@.alertType)]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceAlerts[?(@.priceType)]").isNotEmpty());
        List<String> alertTypes = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.priceAlerts[*].alertType");
        String uuid = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.priceAlerts[0].uuid");
        if (!alertTypes.isEmpty()) {
            for (String alertType : alertTypes) {
                if (alertType.equals("monitor")) {
                    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.priceAlerts[?(@.minPrice)]").isNotEmpty())
                            .andExpect(MockMvcResultMatchers.jsonPath("$.priceAlerts[?(@.maxPrice)]").isNotEmpty());
                }
            }
        }

        //更新成功
        postMan.getExampleRequest("6.1 定制房价预警(更新成功)").replaceUrlKeyValue(ImmutableMap.of("suitCode", suitCode,"uuid",uuid)).doExampleRequest()
                .andExpect(MockMvcResultMatchers.status().isOk());
        //查看
        //6.2 查看房价预警(成功)
        postMan.getExampleRequest("6.2 查看房价预警(成功)").replaceUrlKeyValue(ImmutableMap.of("suitCode", suitCode)).doExampleRequest()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceAlerts").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceAlerts[?(@.uuid)]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceAlerts[?(@.alertType)]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceAlerts[?(@.priceType)]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceAlerts[0].minPrice").value(333))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceAlerts[0].maxPrice").value(222));

        //删除
        postMan.getExampleRequest("6.1 定制房价预警(取消成功)").replaceUrlKeyValue(ImmutableMap.of("suitCode", suitCode,"uuid",uuid)).doExampleRequest()
                .andExpect(MockMvcResultMatchers.status().isOk());
        //查看
        postMan.getExampleRequest("6.2 查看房价预警(成功)").replaceUrlKeyValue(ImmutableMap.of("suitCode", suitCode)).doExampleRequest()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceAlerts").isEmpty());
    }

    @Test
    public void getriceAlert() throws Exception {
        postMan.doMissArgument(ImmutableList.of(
                "6.2 查看房价预警(无suitCode)"
        ));
    }

}