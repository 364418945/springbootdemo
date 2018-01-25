package common;

import java.util.Arrays;

public class PostManRequest {

    private String id;

    private String headers;

    private HeaderData[] headerData;

    private String url;

    private QueryParams[] queryParams;

//    private Object pathVariables;

//    private Object pathVariableData;

//    private Object events;

    private String auth;

    private String method;

    private String collectionId;

    private Object data;

    private String dataMode;

    private String name;//接口名称

    private String description;//接口描述

    private String descriptionFormat;//

    private Long time;

    private Double version;

    private PostManResponse[] responses;

    private String rawModeData;

    private String collection_id;

//    private Object currentHelper;
//
//    private Object helperAttributes;



    public class HeaderData {

        private String key;

        private String value;

        private String description;

        private boolean enabled;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public class QueryParams extends HeaderData {
        private boolean equals;

        public boolean isEquals() {
            return equals;
        }

        public void setEquals(boolean equals) {
            this.equals = equals;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
//        if(data instanceof String){
//            this.data = TestCommon.removeSymbol(String.valueOf(data));
//        }else{
            this.data = data;
//        }
    }

    public String getDataMode() {
        return dataMode;
    }

    public void setDataMode(String dataMode) {
        this.dataMode = dataMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionFormat() {
        return descriptionFormat;
    }

    public void setDescriptionFormat(String descriptionFormat) {
        this.descriptionFormat = descriptionFormat;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public PostManResponse[] getResponses() {
        return responses;
    }

    public void setResponses(PostManResponse[] responses) {
        this.responses = responses;
    }

    public String getRawModeData() {
        return rawModeData;
    }

    public void setRawModeData(String rawModeData) {
        this.rawModeData = rawModeData;
    }

    public String getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(String collection_id) {
        this.collection_id = collection_id;
    }

    public HeaderData[] getHeaderData() {
        return headerData;
    }

    public void setHeaderData(HeaderData[] headerData) {
        this.headerData = headerData;
    }

    public QueryParams[] getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(QueryParams[] queryParams) {
        this.queryParams = queryParams;
    }

    @Override
    public String toString() {
        return "PostManRequest{" +
                "headers='" + headers + '\'' +
                ", headerData=" + Arrays.toString(headerData) +
                ", url='" + url + '\'' +
                ", queryParams=" + Arrays.toString(queryParams) +
                ", method='" + method + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
