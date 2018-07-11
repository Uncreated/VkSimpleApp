package com.uncreated.vksimpleapp.model.repository.user;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.WebRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class UserWebRepository extends WebRepository<User> {

    public UserWebRepository(ApiService apiService, EventBus eventBus) {
        super(apiService, eventBus);
    }

    @Override
    protected void applyEventBus(ApiService apiService, EventBus eventBus) {
        compositeDisposable.add(eventBus.getAuthSubject()
                .observeOn(Schedulers.io())
                .subscribe(auth -> getUser(apiService, eventBus, auth)
                        .subscribe(user -> eventBus.getUserSubject().onNext(user),
                                throwable -> eventBus.getMessageSubject().onNext(throwable.getMessage()))));
    }

    Observable<User> getUser(ApiService apiService, EventBus eventBus, Auth auth) {
        return apiService.getUser(auth.getUserId(), "photo_max")
                .map(vkResponse -> {
                    List<User> users = vkResponse.getResponse();
                    if (users != null && users.size() > 0) {
                        return users.get(0);
                    } else {
                        errorHandle(vkResponse.getError(), eventBus);
                        throw new RequestException(vkResponse.getError());
                    }
                });
    }
}
