package com.uncreated.vksimpleapp.model.repository;

import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryRepository;
import com.uncreated.vksimpleapp.model.repository.photo.PhotoRepository;
import com.uncreated.vksimpleapp.model.repository.settings.ISettingsRepository;
import com.uncreated.vksimpleapp.model.repository.user.UserRepository;

public class Repositories {

    private IAuthRepository authRepository;
    private PhotoRepository photoRepository;

    private GalleryRepository galleryRepository;
    private UserRepository userRepository;

    private ISettingsRepository settingsRepository;

    public Repositories(IAuthRepository authRepository,
                        PhotoRepository photoRepository,
                        GalleryRepository galleryRepository,
                        UserRepository userRepository,
                        ISettingsRepository settingsRepository) {
        this.authRepository = authRepository;
        this.photoRepository = photoRepository;
        this.galleryRepository = galleryRepository;
        this.userRepository = userRepository;
        this.settingsRepository = settingsRepository;
    }
}

