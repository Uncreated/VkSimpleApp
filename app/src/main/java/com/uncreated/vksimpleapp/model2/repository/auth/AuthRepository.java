package com.uncreated.vksimpleapp.model2.repository.auth;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model2.entity.vk.Auth;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class AuthRepository {

    private static final String KEY_AUTH = "keyAuth";

    private SharedPreferences sharedPreferences;

    private Subject<Auth> authSubject;

    public AuthRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

        Auth currentAuth = Auth.load(sharedPreferences, KEY_AUTH);

        if (currentAuth != null && currentAuth.isValid()) {
            authSubject = BehaviorSubject.createDefault(currentAuth);
        } else {
            authSubject = BehaviorSubject.createDefault(Auth.AuthNotValid());
        }
    }

    public void setAuth(Auth auth) {
        auth.save(sharedPreferences, KEY_AUTH);
        authSubject.onNext(auth);
    }

    public Observable<Auth> getAuthObservable() {
        return authSubject;
    }
}
