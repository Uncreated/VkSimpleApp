package com.uncreated.vksimpleapp.model.repository.user;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.WebRepository;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UserRepository extends WebRepository {

    public UserRepository(ApiService apiService, EventBus eventBus) {
        Disposable disposable = eventBus.getAuthSubject()
                .observeOn(Schedulers.io())
                .subscribe(auth -> getUser(apiService, eventBus, auth));
    }

    private Disposable getUser(ApiService apiService, EventBus eventBus, Auth auth) {
        return apiService.getUser(auth.getUserId(), "photo_max")
                .subscribe(vkResponse -> {
                            List<User> users = vkResponse.getResponse();
                            if (users != null && users.size() > 0) {
                                eventBus.getUserSubject()
                                        .onNext(users.get(0));
                            } else {
                                errorHandle(vkResponse.getError(), eventBus);
                            }
                        },
                        throwable -> {
                            Timber.tag("MyDebug").d(throwable);
                        });
    }
}
