package com.cqjtu.csi.model.param;

import com.cqjtu.csi.model.dto.base.InputConverter;
import com.cqjtu.csi.model.entity.Employee;

/**
 * @author mumu
 * @date 2020/2/16
 */
public class EmployeeParam implements InputConverter<Employee> {

    private String name;

    private String cardId;

    private String phone;

    private String sex;

    private Integer jobId;

    private Integer deptId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "EmployeeParam{" +
                "name='" + name + '\'' +
                ", cardId='" + cardId + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", jobId=" + jobId +
                ", deptId=" + deptId +
                '}';
    }
}
