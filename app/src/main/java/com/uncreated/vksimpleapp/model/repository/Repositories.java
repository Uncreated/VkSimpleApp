package com.uncreated.vksimpleapp.model.repository;

import com.uncreated.vksimpleapp.model.api.VkErrorHandler;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryRepository;
import com.uncreated.vksimpleapp.model.repository.photo.PhotoRepository;
import com.uncreated.vksimpleapp.model.repository.user.UserRepository;

public class Repositories {

    private IAuthRepository authRepository;
    private PhotoRepository photoRepository;
    private VkErrorHandler vkErrorHandler;

    private GalleryRepository galleryRepository;
    private UserRepository userRepository;

    public Repositories(IAuthRepository authRepository,
                        PhotoRepository photoRepository,
                        VkErrorHandler vkErrorHandler,
                        GalleryRepository galleryRepository,
                        UserRepository userRepository) {
        this.authRepository = authRepository;
        this.photoRepository = photoRepository;
        this.vkErrorHandler = vkErrorHandler;
        this.galleryRepository = galleryRepository;
        this.userRepository = userRepository;
    }
}

