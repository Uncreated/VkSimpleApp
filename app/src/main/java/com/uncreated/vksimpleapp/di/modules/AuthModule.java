package com.uncreated.vksimpleapp.di.modules;

import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.ui2.fragments.auth.viewmodel.AuthWebViewClient;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthModule {
    @Provides
    public AuthWebViewClient authWebViewClient(IAuthRepository authRepository,
                                               @Named("version") String version) {
        return new AuthWebViewClient(authRepository, version);
    }
}
