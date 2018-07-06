package com.uncreated.vksimpleapp.di.modules;

import com.squareup.picasso.Picasso;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.repository.Repository;
import com.uncreated.vksimpleapp.model.repository.gallery.IGalleryRepository;
import com.uncreated.vksimpleapp.model.repository.gallery.WebGalleryRepository;
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
                                    @Named("web") IGalleryRepository galleryRepository) {
        return new Repository(userRepository, galleryRepository);
    }

    @Named("web")
    @Provides
    public IUserRepository webUserRepository(ApiService apiService) {
        return new UserRepositoryWeb(apiService);
    }

    @Named("web")
    @Provides
    public IGalleryRepository webGalleryRepository(ApiService apiService) {
        return new WebGalleryRepository(apiService);
    }

    @Named("cache")
    @Provides
    public Repository cacheRepository(@Named("cache") IUserRepository userRepository,
                                      @Named("web") IGalleryRepository galleryRepository) {
        return webRepository(userRepository, galleryRepository);
    }

    @Named("cache")
    @Provides
    public IUserRepository cacheUserRepository(ApiService apiService) {
        return webUserRepository(apiService);
    }

    @Named("cache")
    @Provides
    public IGalleryRepository cacheGalleryRepository(ApiService apiService) {
        return webGalleryRepository(apiService);
    }

    @Provides
    public Picasso picasso() {
        return Picasso.get();
    }
}
