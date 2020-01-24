package com.cqjtu.csi.model.entity;


import cn.hutool.crypto.digest.BCrypt;

import javax.persistence.*;
import java.util.Date;

/**
 * @author mumu
 * @date 2020/1/11
 */
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

    /**
     * 在持久化(也就是存入数据库)之前对User的密码进行加密，
     * 如果权限为空，默认设置权限为普通用户。
     *
     * <p>
     * 不使用数据库加密是为了提升性能安全性。
     * <p>
     * 加密采用BCrypt, 安全性很高。进过观察，在不同时间相同的密码加密后的结果
     * 是不一样的，也就是不同的时间相同的密码经过加密后的密文是不一样的。
     * <p>
     * 利用BCrypt.checkpw方法能够检查外部输入密码的正确性。
     * <p>
     * 虽然不同时间的密文是不一样的，但依然能够对密码进行验证，这是令人佩服的。
     * <p>
     * 如果用户忘记了密码，那么只能从新设置密码，无法将以前的密码告知用户。即使是数据库
     * 管理员也无法知道原密码(未经过加密的密码)。这种方式也与其他大多数软件相符，比如QQ，
     * 忘记了QQ密码后只能重新设置密码。
     * </p>
     */
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
