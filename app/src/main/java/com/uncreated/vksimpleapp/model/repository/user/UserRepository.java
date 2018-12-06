package com.uncreated.vksimpleapp.model.repository.user;

import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import io.reactivex.Observable;
import timber.log.Timber;

public class UserRepository {
    private UserWebLoader userWebLoader;
    private UserStorageLoader userStorageLoader;

    public UserRepository(UserWebLoader userWebLoader,
                          UserStorageLoader userStorageLoader) {
        this.userWebLoader = userWebLoader;
        this.userStorageLoader = userStorageLoader;
    }

    public Observable<User> getUserObservable(String userId) {
        return Observable.create(emitter -> {
            User user = null;
            try {
                user = userWebLoader.loadUser(userId);
                userStorageLoader.saveUser(user);
            } catch (RequestException e) {
                if (!userWebLoader.handleVkError(e.getRequestError())) {
                    user = userStorageLoader.loadUser(userId);
                }
            } catch (Exception e) {
                Timber.e(e);
                user = userStorageLoader.loadUser(userId);
            }
            emitter.onNext(user);
        });
    }
}
