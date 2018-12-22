package com.uncreated.vksimpleapp.model2.repository.user;

import android.support.annotation.NonNull;

import com.uncreated.vksimpleapp.model2.entity.vk.User;

import java.io.IOException;

import io.reactivex.Observable;

public class UserRepository {
    private UserLoader userWebLoader;
    private UserLoader userStorageLoader;

    public UserRepository(UserLoader userWebLoader,
                          UserLoader userStorageLoader) {
        this.userWebLoader = userWebLoader;
        this.userStorageLoader = userStorageLoader;
    }

    @NonNull
    public Observable<User> load(@NonNull UserParameters userParameters) {
        return Observable.create(emitter -> {
            User user;
            try {
                user = userWebLoader.load(userParameters);
                userStorageLoader.save(userParameters, user);
            } catch (IOException e) {
                user = userStorageLoader.load(userParameters);
            }
            emitter.onNext(user);
        });
    }
}
