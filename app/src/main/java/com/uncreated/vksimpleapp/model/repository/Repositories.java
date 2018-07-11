package com.uncreated.vksimpleapp.model.repository;

import com.uncreated.vksimpleapp.model.api.VkErrorHandler;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.model.repository.photo.PhotoRepository;

public class Repositories {

    private IAuthRepository authRepository;
    private Repository<User> userRepository;
    private Repository<Gallery> galleryRepository;
    private PhotoRepository photoRepository;
    private VkErrorHandler vkErrorHandler;

    public Repositories(IAuthRepository authRepository,
                        Repository<User> userRepository,
                        Repository<Gallery> galleryRepository,
                        PhotoRepository photoRepository,
                        VkErrorHandler vkErrorHandler) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.galleryRepository = galleryRepository;
        this.photoRepository = photoRepository;
        this.vkErrorHandler = vkErrorHandler;
    }
}

