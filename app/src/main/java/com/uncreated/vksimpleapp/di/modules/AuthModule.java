package com.uncreated.vksimpleapp.di.modules;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model.auth.AuthWebClient;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;
import com.uncreated.vksimpleapp.model.repository.auth.AuthRepository;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthModule {
    @Singleton
    @Provides
    public IAuthRepository authRepository(EventBus eventBus,
                                          @Named("Auth") SharedPreferences sharedPreferences) {
        return new AuthRepository(sharedPreferences, eventBus);
    }

    @Singleton
    @Provides
    public AuthWebClient authWebClient(EventBus eventBus, @Named("version") String version) {
        return new AuthWebClient(eventBus, version);
    }
}
