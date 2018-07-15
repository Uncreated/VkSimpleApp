package com.uncreated.vksimpleapp.model.entity.vk;

import com.uncreated.vksimpleapp.model.common.Utils;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Auth) {
            Auth auth = (Auth) obj;
            return Utils.equals(auth.userId, this.userId) &&
                    Utils.equals(auth.accessToken, this.accessToken) &&
                    Utils.equals(auth.expiredDate, this.expiredDate) &&
                    Utils.equals(auth.valid, this.valid) &&
                    Utils.equals(auth.logout, this.logout);
        }
        return false;
    }
}
