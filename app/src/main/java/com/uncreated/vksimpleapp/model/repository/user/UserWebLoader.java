package com.uncreated.vksimpleapp.model.repository.user;

import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.responses.VkResponse;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.WebLoader;

import java.util.List;

import retrofit2.Call;

public class UserWebLoader extends WebLoader<List<User>> {

    private static final String REQUIRED_FIELDS = "photo_max";

    private ApiService apiService;

    public UserWebLoader(ApiService apiService) {
        this.apiService = apiService;
    }

    public User loadUser(String userId) throws Exception {
        Call<VkResponse<List<User>>> call = apiService.getCallUser(userId, REQUIRED_FIELDS);

        List<User> users = load(call);

        return users.get(0);
    }
}
