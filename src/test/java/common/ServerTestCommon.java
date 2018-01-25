package common;


public class ServerTestCommon {

    private String apiKey;

    private String userToken;

    private String ip_port;

    public ServerTestCommon() {

    }

    public ServerTestCommon(String apiKey, String userToken, String ip_port) {
        this.apiKey =apiKey;
        this.userToken = userToken;
        this.ip_port = ip_port;
    }


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getIp_port() {
        return ip_port;
    }

    public void setIp_port(String ip_port) {
        this.ip_port = ip_port;
    }
}
