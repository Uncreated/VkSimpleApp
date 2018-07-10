package com.uncreated.vksimpleapp.model.repository.auth;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthRepository implements IAuthRepository {

    private static final String KEY_USER_ID = "keyUserId";
    private static final String KEY_ACCESS_TOKEN = "keyAccessToken";
    private static final String KEY_EXPIRED_DATE = "keyExpiredDate";

    private SharedPreferences sharedPreferences;

    private Auth currentAuth;

    public AuthRepository(SharedPreferences sharedPreferences, EventBus eventBus) {
        this.sharedPreferences = sharedPreferences;

        loadLastAuth();

        if (currentAuth != null) {
            eventBus.getAuthSubject()
                    .onNext(currentAuth);
        } else {
            eventBus.getAuthNotValidSubject()
                    .onNext("");
        }

        Disposable disposable = eventBus.getAuthSubject()
                .observeOn(Schedulers.io())
                .subscribe(auth -> {
                    currentAuth = auth;
                    sharedPreferences.edit()
                            .putString(KEY_USER_ID, auth.getUserId())
                            .putString(KEY_ACCESS_TOKEN, auth.getAccessToken())
                            .putLong(KEY_EXPIRED_DATE, auth.getExpiredDate())
                            .apply();
                });
    }

    private void loadLastAuth() {
        String userId = sharedPreferences.getString(KEY_USER_ID, null);
        String accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
        Long expiredDate = sharedPreferences.getLong(KEY_EXPIRED_DATE, 0);
        if (userId != null && accessToken != null) {
            currentAuth = new Auth(userId, accessToken, expiredDate);
        }
    }
}
