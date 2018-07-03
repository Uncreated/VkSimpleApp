package com.uncreated.vksimpleapp.model.repository.auth;

import com.uncreated.vksimpleapp.model.entity.Auth;

import java.util.List;

public interface IAuthRepository {
    Auth getCurrentAuth();

    void setCurrentAuth(Auth auth);

    List<Auth> getAuthList();
}
