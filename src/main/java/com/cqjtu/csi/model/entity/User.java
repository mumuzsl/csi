package com.cqjtu.csi.model.entity;


import cn.hutool.crypto.digest.BCrypt;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login_name", columnDefinition = "varchar(20) not null")
    private String loginName;

    @Column(name = "password", columnDefinition = "varchar(255) not null")
    private String password;

    @Column(name = "status", columnDefinition = "int(11) default 2")
    private Integer status;

    @Column(name = "username", columnDefinition = "varchar(20) not null")
    private String username;

    @Column(name = "faceurl")
    private String faceurl;

    @Column(name = "facepath")
    private String facepath;

    @Override
    public void prePersist() {
        super.prePersist();

        if (password != null) {
            this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        }

        if (status == null) {
            this.status = 2;
        }
    }

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
