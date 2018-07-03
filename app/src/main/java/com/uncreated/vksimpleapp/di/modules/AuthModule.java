package com.uncreated.vksimpleapp.di.modules;

import android.content.SharedPreferences;

import com.uncreated.vksimpleapp.model.repository.auth.AuthRepository;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthModule {
    @Provides
    public IAuthRepository authRepository(@Named("Auth") SharedPreferences sharedPreferences) {
        return new AuthRepository(sharedPreferences);
    }
}
