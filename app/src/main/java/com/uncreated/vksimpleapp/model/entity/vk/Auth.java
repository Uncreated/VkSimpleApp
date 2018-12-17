package com.uncreated.vksimpleapp.model.entity.vk;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.uncreated.vksimpleapp.model.common.Utils;

public class Auth {
    private String userId;
    private String accessToken;
    private Long expiredDate;
    private boolean valid = true;

    public Auth(String userId, String accessToken, Long expiredDate) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.expiredDate = expiredDate;
    }

    private Auth() {
        this.valid = false;
    }

    public static Auth AuthNotValid() {
        return new Auth();
    }

    public boolean isValid() {
        return valid;//TODO: check expired date
    }

    public static Long calcExpiredDate(long lifeTime) {
        return System.currentTimeMillis() + lifeTime;
    }

    public String getUserId() {
        //return userId;//TODO: return the value
        return "1";
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
                    Utils.equals(auth.valid, this.valid);
        }
        return false;
    }

    public static Auth load(SharedPreferences preferences, String key) {
        return new Gson().fromJson(preferences.getString(key, ""), Auth.class);
    }

    public boolean save(SharedPreferences preferences, String key) {
        return preferences.edit()
                .putString(key, new Gson().toJson(this))
                .commit();
    }
}
