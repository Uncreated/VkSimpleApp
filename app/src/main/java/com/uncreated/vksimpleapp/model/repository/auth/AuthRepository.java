package com.uncreated.vksimpleapp.model.repository.auth;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;

import io.reactivex.schedulers.Schedulers;

public class AuthRepository implements IAuthRepository {

    private static final String KEY_USER_ID = "keyUserId";
    private static final String KEY_ACCESS_TOKEN = "keyAccessToken";
    private static final String KEY_EXPIRED_DATE = "keyExpiredDate";

    private SharedPreferences sharedPreferences;

    public AuthRepository(SharedPreferences sharedPreferences, EventBus eventBus) {
        this.sharedPreferences = sharedPreferences;

        Auth currentAuth = loadAuth();

        if (currentAuth != null) {
            eventBus.authPost(currentAuth);
        } else {
            eventBus.authPost(Auth.AuthNotValid());
        }

        eventBus.authSubscribe(auth -> {
            if (auth.isValid()) {
                saveAuth(auth);
            }
        }, Schedulers.io());
    }

    private void saveAuth(Auth auth) {
        sharedPreferences.edit()
                .putString(KEY_USER_ID, auth.getUserId())
                .putString(KEY_ACCESS_TOKEN, auth.getAccessToken())
                .putLong(KEY_EXPIRED_DATE, auth.getExpiredDate())
                .apply();
    }

    private Auth loadAuth() {
        String userId = sharedPreferences.getString(KEY_USER_ID, null);
        String accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
        Long expiredDate = sharedPreferences.getLong(KEY_EXPIRED_DATE, 0);
        if (userId != null && accessToken != null) {
            return new Auth(userId, accessToken, expiredDate);
        }
        return null;
    }
}
