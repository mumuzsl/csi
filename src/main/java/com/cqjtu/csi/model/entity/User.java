package com.cqjtu.csi.model.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login_name", columnDefinition = "varchar(20) not null")
    private String loginName;

    @Column(name = "password", columnDefinition = "varchar(32) not null")
    private String password;

    @Column(name = "status", columnDefinition = "int(11) default 2")
    private Integer status;

    @Column(name = "create_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "username", columnDefinition = "varchar(20) not null")
    private String username;

    @Column(name = "faceurl")
    private String faceurl;

    @Column(name = "facepath")
    private String facepath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFaceurl() {
        return faceurl;
    }

    public void setFaceurl(String faceurl) {
        this.faceurl = faceurl;
    }

    public String getFacepath() {
        return facepath;
    }

    public void setFacepath(String facepath) {
        this.facepath = facepath;
    }
}
