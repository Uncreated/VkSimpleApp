package com.uncreated.vksimpleapp.di.modules;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model2.repository.auth.AuthRepository;
import com.uncreated.vksimpleapp.ui2.fragments.auth.viewmodel.AuthWebViewClient;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthModule {

    @Singleton
    @Provides
    public AuthRepository authRepository(@Named("Auth") SharedPreferences sharedPreferences) {
        return new AuthRepository(sharedPreferences);
    }

    @Provides
    public AuthWebViewClient authWebViewClient(AuthRepository authRepository,
                                               @Named("version") String version) {
        return new AuthWebViewClient(authRepository, version);
    }
}
