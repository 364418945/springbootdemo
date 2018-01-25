package common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;

/**
 * 根据postman导出文件生成测试代码 v1格式
 * （暂时不支持文件上传的接口）
 * 启动测试前生成好，通过test调用
 * author:sunsheng
 */
@Slf4j
public class PostMan_v1 {
    //    private String filePath = "./avm1.0支付.postman_collection.json";//"./avm2.0new.postman_collection.json"
//    private String filePath = "./testAll.postman_collection.json";//"./avm2.0new.postman_collection.json"
    private String filePath;//"./avm2.0new.postman_collection.json"
    private PostManOutput_v1 postManOutput;//post脚本json对象
    private HttpHeaders httpHeaders;//头信息
    private MockHttpServletRequestBuilder mockHttpServletRequestBuilder;//mockrequestbuilder
    private MockMvc mockMvc;//
    //    ResultActions resultActions;
//    ExpectContentCallable expectContentCallable;//预期结果回调接口
    public static Map<String, PostManResponse> map = new HashMap<>();//存储请求
    private static boolean initMap = false;//是否加载过map
    private String url;
    private Object requestBody;
    private PostManRequest.HeaderData[] headerDatas;
    private String method;
    Gson gson = new GsonBuilder().create();

    public PostMan_v1() {

    }

    public PostMan_v1(String filePath, MockMvc mockMvc) {
        this.filePath = filePath;
        this.mockMvc = mockMvc;
        if (!initMap) {
            parseFile().parseReqests();
        }
    }


    private PostMan_v1 parseFile() {
        log.info("运行测试============================================================================================");

        try {
            Reader reader = new FileReader(new File(filePath));
            postManOutput = gson.fromJson(reader, PostManOutput_v1.class);
            log.debug(String.format("解析内容：%s", postManOutput.toString()));
            return this;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void parseReqests() {
        PostManRequest[] postManRequests = postManOutput.getRequests();
        if (!ObjectUtils.isEmpty(postManRequests)) {
            for (int i = 0; i < postManRequests.length; i++) {//获取所有collections下的接口
                PostManResponse[] postManResponsesExample = postManRequests[i].getResponses();
                if (!ObjectUtils.isEmpty(postManResponsesExample)) {
                    for (int r = 0; r < postManResponsesExample.length; r++) {
                        PostManResponse postManResponse = postManResponsesExample[r];
                        //处理当前example的请求信息
                        requestToMap(postManResponse);
//                        doInnerExampleRequest(postManResponse);
                    }
                }
            }
        }
    }


    private void requestToMap(PostManResponse postManResponse) {
        String name = postManResponse.getName();
        PostManResponse _postManResponse = map.get(name);
        if (_postManResponse == null) {
            map.put(name, postManResponse);
        }
        initMap = true;
    }


    public PostMan_v1 getExampleRequest(String name) {
        PostManResponse postManResponse = map.get(name);
        log.info(String.format("调用接口：%s", name));
        PostManRequest postManRequest = postManResponse.getRequest();
        url = postManRequest.getUrl();//获取url和queryParams
        method = postManRequest.getMethod();//获取执行方法类型
        headerDatas = postManRequest.getHeaderData();
        requestBody = postManRequest.getData();//获取requestBody信息
        return this;
    }

    public ResultActions doExampleRequest() {
        log.debug(String.format("url:%s method:%s headerDatas:%s requestBody:%s", url, method, headerDatas, requestBody));
        doSpecifiedRequestMethod(method, url);
        doHttpHeaders(headerDatas);
        doContent(requestBody);
        try {
            return mockMvc.perform(mockHttpServletRequestBuilder);
        } catch (Exception e) {
            log.error("执行测试出错", e);
        }
        return null;
    }


    /**
     * 替换url中指定的key和value
     *
     * @param params
     * @return
     */
    public PostMan_v1 replaceUrlKeyValue(Map<String, String> params) {
        if (params != null) {
            for (String key : params.keySet()) {
                if (url.indexOf(key) > 0) {//指定key存在则替换
                    String before = url.substring(0, url.indexOf(key));
                    String after = url.substring(url.indexOf(key));
                    if (after.indexOf("&") > 0) {
                        after = after.substring(after.indexOf("&"));
                    } else {
                        after = "";
                    }
                    url = before + key + "=" + params.get(key) + "&" + after;

                } else {//不存在直接添加
                    url = url + "&" + key + "=" + params.get(key);
                }
            }
            if (url.endsWith("&")) {
                url = url.substring(0, url.length() - 1);
            }
        }
        return this;
    }


    /**
     * 替换RequestBody中指定的key和value
     *
     * @param params
     * @return
     */
    public PostMan_v1 replaceBodyKeyValue(Map<String, String> params) {
        if (params != null && requestBody != null) {
            Map map = gson.fromJson(requestBody.toString(), Map.class);
            for (String key : params.keySet()) {
                map.put(key, params.get(key));
            }
            requestBody = gson.toJson(map);
        }
        return this;
    }

//    @Deprecated
//    public void doInnerExampleRequest(PostManResponse postManResponse) {
//        Integer code = postManResponse.getResponseCode().getCode();//获取执行状态结果
//        String name = postManResponse.getName();
//        log.info(String.format("调用接口：%s", name));
//        PostManRequest postManRequest = postManResponse.getRequest();
//        String url = postManRequest.getUrl();//获取url和queryParams
//        String method = postManRequest.getMethod();//获取执行方法类型
//        PostManRequest.HeaderData[] headerDatas = postManRequest.getHeaderData();
//        Object requestBody = postManRequest.getData();//获取requestBody信息
//        doSpecifiedRequestMethod(method, url);
//        doHttpHeaders(headerDatas);
//        doContent(requestBody);
//        try {
//            resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
//            doExpect(code);
//        } catch (Exception e) {
//            log.error("执行测试出错", e);
//        }
//    }


    /**
     * 期待结果
     *
     * @param code
     * @throws Exception
     */
//    @Deprecated
//    private void doExpect(Integer code) throws Exception {
//        if (code != null) {
//            resultActions.andExpect(MockMvcResultMatchers.status().is(code));
//            log.info("验证状态成功！");
//        }
//        resultActions.andExpect(expectContentCallable.getResultMatchers());
//
//    }

    /**
     * 添加请求数据
     *
     * @param requestBody
     */
    private void doContent(Object requestBody) {
        if (!ObjectUtils.isEmpty(requestBody)) {
            mockHttpServletRequestBuilder.content(requestBody.toString());
        }
    }

    /**
     * 处理表头信息
     *
     * @param headerDatas
     */
    private void doHttpHeaders(PostManRequest.HeaderData[] headerDatas) {
        if (!ObjectUtils.isEmpty(headerDatas)) {
            httpHeaders = new HttpHeaders();
            for (int i1 = 0; i1 < headerDatas.length; i1++) {
                PostManRequest.HeaderData _headerdata = headerDatas[i1];
                String headerKey = _headerdata.getKey();
                String headerValue = _headerdata.getValue();
                httpHeaders.add(headerKey, headerValue);//设置httpHead                 }
            }
            mockHttpServletRequestBuilder.headers(httpHeaders);
        }
    }

    /**
     * 更具不同方法类型获取对象
     *
     * @param method
     * @param uri
     */
    private void doSpecifiedRequestMethod(String method, String uri) {
        switch (method) {
            case "GET":
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(uri);
                break;
            case "POST":
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.post(uri);
                break;
            case "DELETE":
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.delete(uri);
                break;
            case "PUT":
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.put(uri);
                break;
            default:
                log.info("赞不支持其他方法类型");
                break;
        }
    }


    public void doMissArgument(List<String> names) throws Exception {
        for (String name : names) {
            getExampleRequest(name).doExampleRequest()
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error.detail", containsString("缺失必选参数")));
        }
    }

}
