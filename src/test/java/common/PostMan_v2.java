package common;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
public class PostMan_v2 {
    Logger log = LoggerFactory.getLogger(getClass());
    private Resource resource;
    private String filePath;//"./avm2.0new.postman_collection.json"
    private PostManOutput_v2 postManOutput;//post脚本json对象
    private HttpHeaders httpHeaders;//头信息
    private MockHttpServletRequestBuilder mockHttpServletRequestBuilder;//mockrequestbuilder
    private MockMvc mockMvc;//
    public static Map<String, PostManResponse_v2> map = new HashMap<>();//存储请求
    private static boolean initMap = false;//是否加载过map
    private OriginalRequest.Url url;
    private OriginalRequest.Body requestBody;
    private OriginalRequest.Header[] headerDatas;
    private String method;
    MockMultipartFile mockMultipartFile;
    Gson gson = new GsonBuilder().create();
    ServerTestCommon serverTestCommon;

    public PostMan_v2(){
    }

    public PostMan_v2(MockMvc mockMvc, Resource resource) {
        this.filePath = filePath;
        this.mockMvc = mockMvc;
        this.resource = resource;
        if (!initMap) {
            parseFile().parseReqests();
        }
    }

    public PostMan_v2(MockMvc mockMvc, Resource resource, ServerTestCommon serverTestCommon) {
        this(mockMvc,resource);
        this.serverTestCommon = serverTestCommon;
    }

    private PostMan_v2 parseFile() {
        try {
            Reader reader = new InputStreamReader(resource.getInputStream());
            postManOutput = gson.fromJson(reader, PostManOutput_v2.class);
            log.info(String.format("解析内容：%s", postManOutput.toString()));
            return this;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void parseReqests() {
        initMap = true;
        List<PostManItem> postManItems = postManOutput.getItem();
        if (!ObjectUtils.isEmpty(postManItems)) {
            for (int i = 0; i < postManItems.size(); i++) {//获取所有collections下的接口
                List<PostManResponse_v2> PostManResponse_v2s = postManItems.get(i).getResponse();
                if (!ObjectUtils.isEmpty(PostManResponse_v2s)) {
                    for (int r = 0; r < PostManResponse_v2s.size(); r++) {
                        PostManResponse_v2 postManResponse = PostManResponse_v2s.get(r);
                        //处理当前example的请求信息
                        requestToMap(postManResponse);
//                        doInnerExampleRequest(postManResponse);
                    }
                }
            }
        }
    }


    private void requestToMap(PostManResponse_v2 postManResponse) {
        String name = postManResponse.getName();
        PostManResponse_v2 _postManResponse = map.get(name);
        if (_postManResponse == null) {
            map.put(name, postManResponse);
        }
    }


    public PostMan_v2 getExampleRequest(String name) {
        PostManResponse_v2 postManResponse = map.get(name);
        log.info(String.format("调用接口：%s", name));
        OriginalRequest originalRequest = postManResponse.getOriginalRequest();
        url = originalRequest.getUrl();//获取url和queryParams
        method = originalRequest.getMethod();//获取执行方法类型
        headerDatas = originalRequest.getHeader();
        requestBody = originalRequest.getBody();//获取requestBody信息
        return this;
    }

    public ResultActions doExampleRequest() {
        log.debug(String.format("url:%s method:%s headerDatas:%s requestBody:%s", url, method, headerDatas, requestBody));
        //判断是否需要修改地址
        if(serverTestCommon!=null){
            String apiKey = serverTestCommon.getApiKey();
            String userToken = serverTestCommon.getUserToken();
            String ipport = serverTestCommon.getIp_port();
            if(!StringUtils.isEmpty(apiKey)&&isApiKey(url.getRaw())){
                replaceUrlKeyValue(ImmutableMap.of("apiKey",apiKey));
            }
            if(!StringUtils.isEmpty(userToken)&&isUserToken(url.getRaw())){
                replaceUrlKeyValue(ImmutableMap.of("userToken",userToken));
            }
            replaceServer(ipport);
        }

        if (isFileMultipart()) {//是否上传文件
            doFileMultipartRequestMethod(url);
        } else {
            doSpecifiedRequestMethod(method, url);
        }
        doHttpHeaders(headerDatas);
        doContent(requestBody);
        try {
            return mockMvc.perform(mockHttpServletRequestBuilder);
        } catch (Exception e) {
            log.error("执行测试出错", e);
        }
        return null;
    }



    public PostMan_v2 createMutipartFile(String name, Resource filePath) throws IOException {
        mockMultipartFile = new MockMultipartFile(name, filePath.getInputStream());
        return this;
    }


    /**
     * 替换url中指定的key和value
     *
     * @param params
     * @return
     */
    public PostMan_v2 replaceUrlKeyValue(Map<String, String> params) {
        String urlraw = url.getRaw();
        if (params != null) {
            for (String key : params.keySet()) {
                if (urlraw.indexOf(key) > 0) {//指定key存在则替换
                    String before = urlraw.substring(0, urlraw.indexOf(key));
                    String after = urlraw.substring(urlraw.indexOf(key));
                    if (after.indexOf("&") > 0) {
                        after = after.substring(after.indexOf("&"));
                    } else {
                        after = "";
                    }
                    urlraw = before + key + "=" + params.get(key)  + after;

                } else {//不存在直接添加
                    urlraw = urlraw + "&" + key + "=" + params.get(key);
                }
            }
            if (urlraw.endsWith("&")) {
                urlraw = urlraw.substring(0, urlraw.length() - 1);
            }
        }
        url.setRaw(urlraw);
        return this;
    }

    /**
     * 替换服务器地址
     * @param ipport
     */
    private void replaceServer(String ipport) {
        String urlraw = url.getRaw();
        if (!StringUtils.isEmpty(url.getProtocol())) {
            urlraw = urlraw.substring(urlraw.indexOf("//")+2);
            url.setProtocol("");
        }
        String after = urlraw.substring(urlraw.indexOf("/"));
        urlraw = ipport+after;
        url.setRaw(urlraw);
    }
    /**
     * 替换RequestBody中指定的key和value
     *
     * @param params
     * @return
     */
    public PostMan_v2 replaceBodyKeyValue(Map<String, String> params) {
        if (params != null && requestBody != null) {
            Map map = gson.fromJson(requestBody.getRaw().toString(), Map.class);
            for (String key : params.keySet()) {
                map.put(key, params.get(key));
            }
            requestBody.setRaw(gson.toJson(map));

        }
        return this;
    }

//    @Deprecated
//    public void doInnerExampleRequest(PostManResponse_v2 postManResponse) {
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
    private void doContent(OriginalRequest.Body requestBody) {
        Object raw = requestBody.getRaw();
        if (!ObjectUtils.isEmpty(raw)) {
            mockHttpServletRequestBuilder.content(raw.toString());
        }
    }

    /**
     * 处理表头信息
     *
     * @param headerDatas
     */
    private void doHttpHeaders(OriginalRequest.Header[] headerDatas) {
        if (!ObjectUtils.isEmpty(headerDatas)) {
            httpHeaders = new HttpHeaders();
            for (int i1 = 0; i1 < headerDatas.length; i1++) {
                OriginalRequest.Header _headerdata = headerDatas[i1];
                String headerKey = _headerdata.getKey();
                String headerValue = _headerdata.getValue();
                httpHeaders.add(headerKey, headerValue);//设置httpHead                 }
            }
            mockHttpServletRequestBuilder.headers(httpHeaders);
        }
    }

    /**
     * 判断content-type是否存在上传文件格式
     */
    private boolean isFileMultipart() {
        String value = null;
        if (headerDatas.length > 0) {
            for (int i = 0; i < headerDatas.length; i++) {
                OriginalRequest.Header header = headerDatas[i];
                if (header.getKey().equals("Content-Type")) {
                    value = header.getValue();
                    break;
                }
            }
        }
        return MediaType.MULTIPART_FORM_DATA_VALUE.equals(value);
    }

    private String getUriStr(OriginalRequest.Url url) {
        String uri = url.getRaw();
        if (StringUtils.isEmpty(url.getProtocol())) {
            uri = "http://" + uri;
        }
        return uri;
    }

    private void doFileMultipartRequestMethod(OriginalRequest.Url url) {
        mockHttpServletRequestBuilder = MockMvcRequestBuilders.fileUpload(getUriStr(url));
        ((MockMultipartHttpServletRequestBuilder) mockHttpServletRequestBuilder).file(mockMultipartFile);
    }

    /**
     * 更具不同方法类型获取对象
     *
     * @param method
     * @param url
     */
    private void doSpecifiedRequestMethod(String method, OriginalRequest.Url url) {

        log.info("请求地址：" + getUriStr(url));
        switch (method) {
            case "GET":
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(getUriStr(url));
                break;
            case "POST":
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.post(getUriStr(url));
                break;
            case "DELETE":
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.delete(getUriStr(url));
                break;
            case "PUT":
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.put(getUriStr(url));
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

    /**
     * 判断是否存在userToken参数
     * @return
     */
    public boolean isUserToken(String uri){
        return uri.indexOf("userToken")>0;
    }

    /**
     * 判断是否存在apiKey参数
     * @return
     */
    public boolean isApiKey(String uri){
        return uri.indexOf("apiKey")>0;
    }



}
