package com.uncreated.vksimpleapp.model.repository.auth;

import com.uncreated.vksimpleapp.model.entity.Auth;

import java.util.List;

public interface IAuthRepository {
    void addAuth(Auth auth);

    List<Auth> getAuthList();
}
