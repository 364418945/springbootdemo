package common;

import java.util.List;

public class PostManItem {

    String name;

    List<PostManResponse_v2> response;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PostManResponse_v2> getResponse() {
        return response;
    }

    public void setResponse(List<PostManResponse_v2> response) {
        this.response = response;
    }
}
