package com.uncreated.vksimpleapp.di.modules;

import com.uncreated.vksimpleapp.model2.api.ApiService;
import com.uncreated.vksimpleapp.model2.loader.WebLoader;
import com.uncreated.vksimpleapp.model2.repository.user.UserLoader;
import com.uncreated.vksimpleapp.model2.repository.user.UserRepository;
import com.uncreated.vksimpleapp.model2.repository.user.loader.UserStorageLoader;
import com.uncreated.vksimpleapp.model2.repository.user.loader.UserWebLoader;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Singleton
    @Provides
    public UserRepository userRepository(@Named("web") UserLoader userWebLoader,
                                         @Named("storage") UserLoader userStorageLoader) {
        return new UserRepository(userWebLoader, userStorageLoader);
    }

    @Named("web")
    @Singleton
    @Provides
    public UserLoader userWebLoader(ApiService apiService, WebLoader webLoader) {
        return new UserWebLoader(apiService, webLoader);
    }

    @Named("storage")
    @Singleton
    @Provides
    public UserLoader userStorageLoader() {
        return new UserStorageLoader();
    }
}
