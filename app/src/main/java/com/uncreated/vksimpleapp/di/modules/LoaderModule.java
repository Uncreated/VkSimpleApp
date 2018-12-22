package com.uncreated.vksimpleapp.di.modules;

import com.uncreated.vksimpleapp.model2.loader.WebLoader;
import com.uncreated.vksimpleapp.model2.repository.auth.AuthRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class LoaderModule {

    @Provides
    public WebLoader webLoader(AuthRepository authRepository) {
        return new WebLoader(authRepository);
    }
}
