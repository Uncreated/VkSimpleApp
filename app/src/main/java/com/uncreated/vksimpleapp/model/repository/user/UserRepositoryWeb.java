package com.uncreated.vksimpleapp.model.repository.user;

import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class UserRepositoryWeb implements IUserRepository {

    private ApiService apiService;

    public UserRepositoryWeb(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<User> get(String userId) {
        return apiService.getUser(userId, "photo_max")
                .subscribeOn(Schedulers.io())
                .map(userResponse -> {
                    List<User> users = userResponse.getResponse();
                    if (users != null && users.size() > 0) {
                        return users.get(0);
                    } else {
                        throw new RequestException(userResponse.getRequestError());
                    }
                });
    }
}
