package com.uncreated.vksimpleapp.model.repository.auth;

import com.uncreated.vksimpleapp.model.entity.vk.Auth;

public interface IAuthRepository {
    Auth getCurrentAuth();

    void setCurrentAuth(Auth auth);
}
