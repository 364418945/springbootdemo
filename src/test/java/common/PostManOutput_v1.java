package common;

import java.util.Arrays;

/**
 * postman导出测试格式类（json格式）
 */

public class PostManOutput_v1 {
    private String id;

    private String name;

    private String description;

    private String[] order;

//    private Object[] folders;

//    private String[] folders_order;

    private long timestamp;

    private String owner;

//    private boolean public;

    private String[] events;

    private String[] variables;

    private String auth;

    private PostManRequest[] requests;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String[] getOrder() {
        return order;
    }

    public void setOrder(String[] order) {
        this.order = order;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String[] getEvents() {
        return events;
    }

    public void setEvents(String[] events) {
        this.events = events;
    }

    public String[] getVariables() {
        return variables;
    }

    public void setVariables(String[] variables) {
        this.variables = variables;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public PostManRequest[] getRequests() {
        return requests;
    }

    public void setRequests(PostManRequest[] requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return "PostManOutput_v1{" +
                "requests=" + Arrays.toString(requests) +
                '}';
    }
}
