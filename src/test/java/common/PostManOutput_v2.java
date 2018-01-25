package common;

import java.util.List;

/**
 * postman导出测试格式类（json格式）
 */

public class PostManOutput_v2 {
    List<PostManItem> item;

    public List<PostManItem> getItem() {
        return item;
    }

    public void setItem(List<PostManItem> item) {
        this.item = item;
    }
}
