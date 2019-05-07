package com.jobs.japan_work.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "userconnection")
public class UserConnection implements Serializable {
 
    private static final long serialVersionUID = -6991752510891411572L;
 
    @Id
    @Column(name = "userid", length = 255, nullable = false)
    private String userId;
 
    @Id
    @Column(name = "providerid", length = 255, nullable = false)
    private String providerId;
 
    @Id
    @Column(name = "provideruserid", length = 255, nullable = false)
    private String providerUserId;
 
    @Column(name = "rank", nullable = false)
    private long rank;
 
    @Column(name = "displayname", length = 255, nullable = true)
    private String displayName;
 
    @Column(name = "profileurl", length = 512, nullable = true)
    private String profileUrl;
 
    @Column(name = "imageurl", length = 512, nullable = true)
    private String imageUrl;
 
    @Column(name = "accesstoken", length = 512, nullable = true)
    private String accessToken;
 
    @Column(name = "secret", length = 512, nullable = true)
    private String secret;
 
    @Column(name = "refreshtoken", length = 512, nullable = true)
    private String refreshToken;
 
    @Column(name = "expiretime", nullable = true)
    private Long expireTime;
 
    public String getUserId() {
        return userId;
    }
 
    public void setUserId(String userId) {
        this.userId = userId;
    }
 
    public String getProviderId() {
        return providerId;
    }
 
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
 
    public String getProviderUserId() {
        return providerUserId;
    }
 
    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }
 
    public long getRank() {
        return rank;
    }
 
    public void setRank(int rank) {
        this.rank = rank;
    }
 
    public String getDisplayName() {
        return displayName;
    }
 
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
 
    public String getProfileUrl() {
        return profileUrl;
    }
 
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
 
    public String getImageUrl() {
        return imageUrl;
    }
 
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
 
    public String getAccessToken() {
        return accessToken;
    }
 
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
 
    public String getSecret() {
        return secret;
    }
 
    public void setSecret(String secret) {
        this.secret = secret;
    }
 
    public String getRefreshToken() {
        return refreshToken;
    }
 
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
 
    public Long getExpireTime() {
        return expireTime;
    }
 
    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
 
}
