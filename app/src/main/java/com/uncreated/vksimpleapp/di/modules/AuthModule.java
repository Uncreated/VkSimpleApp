package com.uncreated.vksimpleapp.di.modules;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model.auth.AuthWebClient;
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
    public IAuthRepository authRepository(@Named("Auth") SharedPreferences sharedPreferences) {
        return new AuthRepository(sharedPreferences);
    }

    @Singleton
    @Provides
    public AuthWebClient authWebClient(@Named("version") String version) {
        return new AuthWebClient(version);
    }
}
