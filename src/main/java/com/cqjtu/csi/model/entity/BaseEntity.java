package com.cqjtu.csi.model.entity;

import com.cqjtu.csi.utils.DataTimeUtils;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class BaseEntity {

    @Column(name = "create_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @PrePersist
    protected void prePersist() {
        if (createTime == null) {
            createTime = DataTimeUtils.now();
        }
    }

}
