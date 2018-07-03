package com.uncreated.vksimpleapp.model.repository.auth;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model.entity.Auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthRepository implements IAuthRepository {

    private SharedPreferences sharedPreferences;

    public AuthRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void addAuth(Auth auth) {
        sharedPreferences.edit()
                .putString(auth.getUserId(), auth.getAccessToken())
                .apply();
    }

    public List<Auth> getAuthList() {
        ArrayList<Auth> authList = new ArrayList<>();

        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            if (entry.getValue() instanceof String) {
                authList.add(new Auth(entry.getKey(), (String) entry.getValue()));
            }
        }

        return authList;
    }
}
