package com.cqjtu.csi.model.dto;

import com.cqjtu.csi.model.dto.base.OutputConverter;
import com.cqjtu.csi.model.entity.Document;

import javax.persistence.Column;

/**
 * @author mumu
 * @date 2020/2/16
 */
public class DocumentDTO extends BaseDTO implements OutputConverter<DocumentDTO, Document> {

    private String title;

    private String filename;

    private String remark;

    private String username;

    @Override
    public String toString() {
        return "DocumentDTO{" +
                "title='" + title + '\'' +
                ", filename='" + filename + '\'' +
                ", remark='" + remark + '\'' +
                ", username='" + username + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
