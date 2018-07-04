package com.uncreated.vksimpleapp.di.modules;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.repository.Repository;
import com.uncreated.vksimpleapp.model.repository.photo.IPhotoRepository;
import com.uncreated.vksimpleapp.model.repository.photo.PhotoRepositoryWeb;
import com.uncreated.vksimpleapp.model.repository.user.IUserRepository;
import com.uncreated.vksimpleapp.model.repository.user.UserRepositoryWeb;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Named("web")
    @Provides
    public Repository webRepository(@Named("web") IUserRepository userRepository,
                                    @Named("web") IPhotoRepository photoRepository) {
        return new Repository(userRepository, photoRepository);
    }

    @Named("web")
    @Provides
    public IUserRepository webUserRepository(ApiService apiService) {
        return new UserRepositoryWeb(apiService);
    }

    @Named("web")
    @Provides
    public IPhotoRepository webPhotoRepository(@Named("application") Context context,
                                               Picasso picasso) {
        return new PhotoRepositoryWeb(context, picasso);
    }

    @Named("cache")
    @Provides
    public Repository cacheRepository(@Named("cache") IUserRepository userRepository,
                                      @Named("cache") IPhotoRepository photoRepository) {
        return webRepository(userRepository, photoRepository);
    }

    @Named("cache")
    @Provides
    public IUserRepository cacheUserRepository(ApiService apiService) {
        return webUserRepository(apiService);
    }

    @Named("cache")
    @Provides
    public IPhotoRepository cachePhotoRepository(@Named("application") Context context,
                                                 Picasso picasso) {
        return webPhotoRepository(context, picasso);
    }

    @Provides
    public Picasso picasso() {
        return Picasso.get();
    }
}
