package com.cqjtu.csi.model.dto;

import com.cqjtu.csi.model.dto.base.InputConverter;
import com.cqjtu.csi.model.dto.base.OutputConverter;
import com.cqjtu.csi.model.entity.Employee;

/**
 * @author mumu
 * @date 2020/1/20
 */
public class EmployeeDTO implements InputConverter<Employee> {

    private Integer id;

    private String name;

    private String sex;

    private Integer jobId;

    private Integer deptId;

    private String jobName;

    private String deptName;

    private String phone;

    private String cardId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
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
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", jobId=" + jobId +
                ", deptId=" + deptId +
                ", jobName='" + jobName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", phone='" + phone + '\'' +
                ", cardId='" + cardId + '\'' +
                '}';
    }
}
