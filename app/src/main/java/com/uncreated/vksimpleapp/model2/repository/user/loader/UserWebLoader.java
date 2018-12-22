package com.uncreated.vksimpleapp.model2.repository.user.loader;

import android.support.annotation.NonNull;

import com.uncreated.vksimpleapp.model2.api.ApiService;
import com.uncreated.vksimpleapp.model2.entity.vk.User;
import com.uncreated.vksimpleapp.model2.loader.WebLoader;
import com.uncreated.vksimpleapp.model2.repository.user.UserLoader;
import com.uncreated.vksimpleapp.model2.repository.user.UserParameters;

import java.io.IOException;

public class UserWebLoader extends UserLoader {

    private static final String REQUIRED_FIELDS = "photo_max";

    private ApiService apiService;
    private WebLoader webLoader;

    public UserWebLoader(ApiService apiService, WebLoader webLoader) {
        this.apiService = apiService;
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public User load(@NonNull UserParameters parameters) throws IOException {
        return webLoader.load(apiService.getCallUser(parameters.getUserId(), REQUIRED_FIELDS))
                .get(0);
    }
}
