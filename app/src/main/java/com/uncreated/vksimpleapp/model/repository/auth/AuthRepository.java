package com.uncreated.vksimpleapp.model.repository.auth;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model.entity.vk.Auth;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class AuthRepository implements IAuthRepository {

    private static final String KEY_AUTH = "keyAuth";

    private SharedPreferences sharedPreferences;

    private Subject<Auth> authSubject = BehaviorSubject.create();

    public AuthRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

        Auth currentAuth = Auth.load(sharedPreferences, KEY_AUTH);

        if (currentAuth != null && currentAuth.isValid()) {
            authSubject.onNext(currentAuth);
        } else {
            authSubject.onNext(Auth.AuthNotValid());
        }
    }

    public void setAuth(Auth auth) {
        if (auth.isValid()) {
            auth.save(sharedPreferences, KEY_AUTH);
        }
        authSubject.onNext(auth);
    }

    public Observable<Auth> getAuthObservable() {
        return authSubject;
    }
}
