package common;

public class PostManResponse {

    private NameCodePair responseCode;

    private String text;

    private String name;

    private PostManRequest request;

    public NameCodePair getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(NameCodePair responseCode) {
        this.responseCode = responseCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
//        this.text = TestCommon.removeSymbol(text);
        this.text = text;
    }

    public PostManRequest getRequest() {
        return request;
    }

    public void setRequest(PostManRequest request) {
        this.request = request;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
