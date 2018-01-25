package common;

public class PostManResponse_v2 {


    private String name;

    private OriginalRequest originalRequest;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OriginalRequest getOriginalRequest() {
        return originalRequest;
    }

    public void setOriginalRequest(OriginalRequest originalRequest) {
        this.originalRequest = originalRequest;
    }
}
