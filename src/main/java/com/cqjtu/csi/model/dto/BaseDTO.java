package com.cqjtu.csi.model.dto;

import java.util.Date;

/**
 * @author mumu
 * @date 2020/2/16
 */
public class BaseDTO {

    protected Integer id;

    protected Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
