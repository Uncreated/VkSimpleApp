package com.uncreated.vksimpleapp.model.entity.vk;

public class Auth {

    private String userId;
    private String accessToken;
    private Long expiredDate;

    public Auth(String userId, String accessToken, Long expiredDate) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.expiredDate = expiredDate;
    }

    public static Long calcExpiredDate(long lifeTime) {
        return System.currentTimeMillis() + lifeTime;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= expiredDate;
    }

    public String getUserId() {
        //return userId;
        return "1";
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Long expiredDate) {
        this.expiredDate = expiredDate;
    }
}
