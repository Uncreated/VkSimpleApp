package com.uncreated.vksimpleapp.model.repository.auth;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model.entity.vk.Auth;

public class AuthRepository implements IAuthRepository {

    private static final String KEY_USER_ID = "keyUserId";
    private static final String KEY_ACCESS_TOKEN = "keyAccessToken";
    private static final String KEY_EXPIRED_DATE = "keyExpiredDate";

    private SharedPreferences sharedPreferences;

    private Auth currentAuth;

    public AuthRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

        loadLastAuth();
    }

    private void loadLastAuth() {
        String userId = sharedPreferences.getString(KEY_USER_ID, null);
        String accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
        Long expiredDate = sharedPreferences.getLong(KEY_EXPIRED_DATE, 0);
        if (userId != null && accessToken != null) {
            currentAuth = new Auth(userId, accessToken, expiredDate);
        }
    }

    public Auth getCurrentAuth() {
        return currentAuth;
    }

    public void setCurrentAuth(Auth auth) {
        currentAuth = auth;
        sharedPreferences.edit()
                .putString(KEY_USER_ID, auth.getUserId())
                .putString(KEY_ACCESS_TOKEN, auth.getAccessToken())
                .putLong(KEY_EXPIRED_DATE, auth.getExpiredDate())
                .apply();
    }
}
