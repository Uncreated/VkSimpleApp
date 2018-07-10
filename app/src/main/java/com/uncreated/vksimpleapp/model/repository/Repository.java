package com.uncreated.vksimpleapp.model.repository;

import com.uncreated.vksimpleapp.model.api.VkErrorHandler;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryRepository;
import com.uncreated.vksimpleapp.model.repository.photo.PhotoRepository;
import com.uncreated.vksimpleapp.model.repository.user.UserRepository;

public class Repository {

    private IAuthRepository authRepository;
    private UserRepository userRepository;
    private GalleryRepository galleryRepository;
    private PhotoRepository photoRepository;
    private VkErrorHandler vkErrorHandler;

    public Repository(IAuthRepository authRepository,
                      UserRepository userRepository,
                      GalleryRepository galleryRepository,
                      PhotoRepository photoRepository,
                      VkErrorHandler vkErrorHandler) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.galleryRepository = galleryRepository;
        this.photoRepository = photoRepository;
        this.vkErrorHandler = vkErrorHandler;
    }
}

