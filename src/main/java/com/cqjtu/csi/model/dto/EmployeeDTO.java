package com.cqjtu.csi.model.dto;

import com.cqjtu.csi.model.dto.base.InputConverter;
import com.cqjtu.csi.model.dto.base.OutputConverter;
import com.cqjtu.csi.model.entity.Employee;

import java.util.Date;

/**
 * @author mumu
 * @date 2020/1/20
 */
public class EmployeeDTO extends BaseDTO implements OutputConverter<EmployeeDTO, Employee> {

    private Integer deptId;

    private String deptName;

    private Integer jobId;

    private String jobName;

    private String name;

    private String cardId;

    private String address;

    private String postCode;

    private String tel;

    private String phone;

    private String qq;

    private String email;

    private String sex;

    private String party;

    private Date birthday;

    private String race;

    private String education;

    private String speciality;

    private String hobby;

    private String remark;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", jobId=" + jobId +
                ", jobName='" + jobName + '\'' +
                ", name='" + name + '\'' +
                ", cardId='" + cardId + '\'' +
                ", address='" + address + '\'' +
                ", postCode='" + postCode + '\'' +
                ", tel='" + tel + '\'' +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", party='" + party + '\'' +
                ", birthday=" + birthday +
                ", race='" + race + '\'' +
                ", education='" + education + '\'' +
                ", speciality='" + speciality + '\'' +
                ", hobby='" + hobby + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
