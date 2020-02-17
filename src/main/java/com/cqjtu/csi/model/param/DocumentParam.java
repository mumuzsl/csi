package com.cqjtu.csi.model.param;

import com.cqjtu.csi.model.dto.base.InputConverter;
import com.cqjtu.csi.model.entity.Document;

/**
 * @author mumu
 * @date 2020/2/2
 */
public class DocumentParam implements InputConverter<Document> {

    private String title;

    private Integer userId;

    private String remark;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "DocumentParam{" +
                "title='" + title + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
