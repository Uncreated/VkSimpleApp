package com.uncreated.vksimpleapp.model.repository.auth;

import com.uncreated.vksimpleapp.model.entity.vk.Auth;

import io.reactivex.Observable;

public interface IAuthRepository {
    void setAuth(Auth auth);

    Observable<Auth> getAuthObservable();
}
