package com.cqjtu.csi.model.entity;

import cn.hutool.core.date.DateTime;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "job_id")
    private Integer jobId;

    @Column(name = "name")
    private String name;

    @Column(name = "card_id")
    private String cardId;

    @Column(name = "address")
    private String address;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "tel")
    private String tel;

    @Column(name = "phone")
    private String phone;

    @Column(name = "qq")
    private String qq;

    @Column(name = "email")
    private String email;

    @Column(name = "sex", columnDefinition = "enum('男', '女')")
    private String sex;

    @Column(name = "party")
    private String party;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "race")
    private String race;

    @Column(name = "education")
    private String education;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "hobby")
    private String hobby;

    @Column(name = "remark")
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getSex() { return sex; }

    public void setSex(String sex) { this.sex = sex; }

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
        return "Employee{" +
                "id=" + id +
                ", deptId=" + deptId +
                ", jobId=" + jobId +
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
