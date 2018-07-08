package com.uncreated.vksimpleapp.model.repository.user;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.responses.VkResponse;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class UserRepositoryWeb implements IUserRepository {

    public UserRepositoryWeb(ApiService apiService, EventBus eventBus) {
        eventBus.getAuthSubject()
                .observeOn(Schedulers.io())
                .map(auth -> {
                    VkResponse<List<User>> userResponse =
                            apiService.getUser(auth.getUserId(), "photo_max")
                                    .execute()
                                    .body();

                    List<User> users = userResponse.getResponse();
                    if (users == null || users.size() == 0) {
                        throw new RequestException(userResponse.getRequestError());
                    }
                    return users.get(0);
                })
                .subscribe(eventBus.getUserSubject());
    }
}
