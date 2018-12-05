package com.uncreated.vksimpleapp.model.repository.user;

import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

public class UserRepository {
    private UserWebLoader userWebLoader;
    private UserStorageLoader userStorageLoader;

    private Subject<User> userSubject = BehaviorSubject.create();

    public UserRepository(UserWebLoader userWebLoader,
                          UserStorageLoader userStorageLoader) {
        this.userWebLoader = userWebLoader;
        this.userStorageLoader = userStorageLoader;
    }

    //TODO:call in main activity start
    public Observable<User> loadUser(EventBus eventBus, Auth auth) {
        String userId = auth.getUserId();

        try {
            User user = userWebLoader.loadUser(userId);
            eventBus.userPost(user);
            userStorageLoader.saveUser(user);
        } catch (RequestException e) {

            if (!userWebLoader.handleVkError(eventBus, e.getRequestError())) {
                loadFromStorage(eventBus, userId);
            }
        } catch (Exception e) {
            Timber.tag("MyDebug").d(e);

            loadFromStorage(eventBus, userId);
        }

        return userSubject;
    }

    private void loadFromStorage(EventBus eventBus, String userId) {
        User user = userStorageLoader.loadUser(userId);
        if (user != null) {
            eventBus.userPost(user);
        }
    }


}
