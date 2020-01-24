package com.cqjtu.csi.model.entity;

import javax.persistence.*;

/**
 * @author ly
 * @data 2020/1/17
 */
@Entity
@Table(name = "facekey")
public class Facekey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "appID")
    private String appID;

    @Column(name = "apiKey")
    private String apiKey;

    @Column(name = "secretKey")
    private String secretKey;

    @Column(name = "threshold")
    private Integer threshold;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
