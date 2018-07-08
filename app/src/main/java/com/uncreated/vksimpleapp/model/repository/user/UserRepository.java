package com.uncreated.vksimpleapp.model.repository.user;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.responses.VkResponse;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import java.util.List;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class UserRepository {

    public UserRepository(ApiService apiService, EventBus eventBus) {
        eventBus.getAuthSubject()
                .observeOn(Schedulers.io())
                .map(auth -> {
                    Response<VkResponse<List<User>>> response =
                            apiService.getUser(auth.getUserId(), "photo_max")
                                    .execute();
                    VkResponse<List<User>> userResponse = response.body();

                    if (userResponse != null) {
                        List<User> users = userResponse.getResponse();
                        if (users != null && users.size() > 0) {
                            return users.get(0);
                        }
                    }
                    if (userResponse != null) {
                        throw new RequestException(userResponse.getRequestError());
                    } else {
                        throw new RuntimeException("empty response");
                    }
                })
                .subscribe(eventBus.getUserSubject());
    }
}
