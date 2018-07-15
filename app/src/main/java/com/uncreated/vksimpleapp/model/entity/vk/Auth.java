package com.uncreated.vksimpleapp.model.entity.vk;

public class Auth {

    private String userId;
    private String accessToken;
    private Long expiredDate;
    private boolean valid = true;
    private boolean logout = false;

    public Auth(String userId, String accessToken, Long expiredDate) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.expiredDate = expiredDate;
    }

    private Auth(boolean logout) {
        this.valid = false;
        this.logout = logout;
    }

    public static Auth AuthNotValid() {
        return new Auth(false);
    }

    public static Auth AuthLogout() {
        return new Auth(true);
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isLogout() {
        return logout;
    }

    public static Long calcExpiredDate(long lifeTime) {
        return System.currentTimeMillis() + lifeTime;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= expiredDate;
    }

    public String getUserId() {
        //return userId;//TODO: return true value
        return "1";//debug
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getExpiredDate() {
        return expiredDate;
    }
}
