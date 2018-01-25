package com.example.demo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.JsonPath;
import common.PostMan_v2;
import common.ServerTestCommon;
import net.minidev.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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

import java.io.File;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootdemoApplication.class)
@AutoConfigureMockMvc//模拟
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ConfigurationProperties(prefix = "server")
public class AssessBatchControllerTest extends ServerTestCommon {
    @Value("classpath:${uploadPath}")
    private Resource uploadPath;

    @Autowired
    MockMvc mockMvc;

    @Value("classpath:${filePath}")
    private Resource resource;

    PostMan_v2 postMan;

    @Before
    public void setUp() {
        postMan = new PostMan_v2(mockMvc,resource,new ServerTestCommon(getApiKey(),getUserToken(),getIp_port()));
    }

    @Test
    public void aimportExcelSuit() throws Exception {
        String name = uploadPath.getFilename();
        postMan.getExampleRequest("3.9导入excel评估(成功)").createMutipartFile(name,uploadPath).doExampleRequest()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"));

    }


    @Test
    public void batchAvmHisList() throws Exception {
        ResultActions resultActions = postMan.getExampleRequest("3.5excel批量评估列表查询(成功)").doExampleRequest()
                .andExpect(MockMvcResultMatchers.status().isOk());
        Integer total = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.page.totalSize");
        Integer perPage = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.page.pageSize");
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.list[?(@.batchno)].length()", hasSize(total > perPage ? perPage : total)));

    }


    @Test
    public void downExcelTemplate() throws Exception {
        ResultActions resultActions = postMan.getExampleRequest("3.6 下载批量导入Excel模板(成功)").doExampleRequest()
                .andExpect(MockMvcResultMatchers.status().isOk());
        File desFile = new File("../123.xlsx");
        if (!desFile.exists()) desFile.createNewFile();
//        FileUtils.writeByteArrayToFile(desFile, resultActions.andReturn().getResponse().getContentAsByteArray());
//        //解析文件
//        ExcelImport excelImport = new ExcelImport(desFile, 0);
//        String value = excelImport.getRow(0).getCell(0).getStringCellValue();
//        Assert.assertEquals("下载失败", "城市（必填）", value);
    }

    @Test
    public void exportExcelSuit() throws Exception {
        postMan.doMissArgument(ImmutableList.of("3.8导出批量评估房产信息(缺少batchno)", "3.8导出批量评估房产信息(缺少type)"));

        ResultActions resultActions = postMan.getExampleRequest("3.5excel批量评估列表查询(成功)").doExampleRequest()
                .andExpect(MockMvcResultMatchers.status().isOk());
        //获得批次号
        String batchno = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.list[0].batchno");
        Integer validcount = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.list[0].validcount");
        Integer nomatchcount = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.list[0].nomatchcount");
        Integer matchcount = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.list[0].matchcount");
        if (validcount > 0) {//查询成功数量
            //3.8导出批量评估房产信息(成功)
            resultActions = postMan.getExampleRequest("3.8导出批量评估房产信息(成功)").replaceUrlKeyValue(ImmutableMap.of("batchno", batchno, "flag", 2 + "")).doExampleRequest()
                    .andExpect(MockMvcResultMatchers.status().isOk());
            Thread.sleep(4 * 1000);
            resultActions = postMan.getExampleRequest("3.5excel批量评估列表查询(成功)").doExampleRequest()
                    .andExpect(MockMvcResultMatchers.status().isOk());
            JSONArray list = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.list[?(@.batchno=='" + batchno + "')]");
            Map<String, Object> map = (Map<String, Object>) list.get(0);
            Map<String, Object> evalSuccessMap = (Map<String, Object>) map.get("evalSuccess");
            Object status = evalSuccessMap.get("status");
            Assert.assertEquals("评估成功下载状态不正确", status + "", "2");
        }
        if (nomatchcount > 0) {//查询失败数量
            //3.8导出批量评估房产信息(成功)
            resultActions = postMan.getExampleRequest("3.8导出批量评估房产信息(成功)").replaceUrlKeyValue(ImmutableMap.of("batchno", batchno, "flag", 1 + "")).doExampleRequest()
                    .andExpect(MockMvcResultMatchers.status().isOk());
            Thread.sleep(4 * 1000);
            resultActions = postMan.getExampleRequest("3.5excel批量评估列表查询(成功)").doExampleRequest()
                    .andExpect(MockMvcResultMatchers.status().isOk());
            JSONArray list = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.list[?(@.batchno=='" + batchno + "')]");
            Map<String, Object> map = (Map<String, Object>) list.get(0);
            Map<String, Object> evalSuccessMap = (Map<String, Object>) map.get("importFail");
            Object status = evalSuccessMap.get("status");
            Assert.assertEquals("导入失败下载状态不正确", status + "", "2");
        }
        if (matchcount > 0) {//查询成功导入数量
            //3.8导出批量评估房产信息(成功)
            resultActions = postMan.getExampleRequest("3.8导出批量评估房产信息(成功)").replaceUrlKeyValue(ImmutableMap.of("batchno", batchno, "flag", 3 + "")).doExampleRequest()
                    .andExpect(MockMvcResultMatchers.status().isOk());
            Thread.sleep(4 * 1000);
            resultActions = postMan.getExampleRequest("3.5excel批量评估列表查询(成功)").doExampleRequest()
                    .andExpect(MockMvcResultMatchers.status().isOk());
            JSONArray list = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.list[?(@.batchno=='" + batchno + "')]");
            Map<String, Object> map = (Map<String, Object>) list.get(0);
            Map<String, Object> evalSuccessMap = (Map<String, Object>) map.get("importSuccess");
            Object status = evalSuccessMap.get("status");
            Assert.assertEquals("导入成功下载状态不正确", status + "", "2");
        }
    }


}