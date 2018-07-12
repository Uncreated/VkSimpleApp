package com.uncreated.vksimpleapp.model.repository.user;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class UserRepository {
    private UserWebLoader userWebLoader;
    private UserStorageLoader userStorageLoader;

    public UserRepository(EventBus eventBus,
                          UserWebLoader userWebLoader,
                          UserStorageLoader userStorageLoader) {
        this.userWebLoader = userWebLoader;
        this.userStorageLoader = userStorageLoader;

        Disposable disposable = eventBus.getAuthSubject()
                .subscribe(auth -> newAuth(eventBus, auth));
    }

    private void newAuth(EventBus eventBus, Auth auth) {
        String userId = auth.getUserId();

        try {
            User user = userWebLoader.loadUser(userId);
            eventBus.getUserSubject()
                    .onNext(user);
            userStorageLoader.saveUser(user);
        } catch (Exception e) {
            Timber.tag("MyDebug").d(e);

            User user = userStorageLoader.loadUser(userId);
            eventBus.getUserSubject()
                    .onNext(user);
        }
    }


}
