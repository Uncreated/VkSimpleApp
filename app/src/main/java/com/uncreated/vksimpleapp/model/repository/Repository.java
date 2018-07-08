package com.uncreated.vksimpleapp.model.repository;

import com.uncreated.vksimpleapp.model.repository.gallery.IGalleryRepository;
import com.uncreated.vksimpleapp.model.repository.photo.IPhotoRepository;
import com.uncreated.vksimpleapp.model.repository.user.IUserRepository;

public class Repository {

    private IUserRepository userRepository;
    private IGalleryRepository galleryRepository;
    private IPhotoRepository photoRepository;

    public Repository(IUserRepository userRepository,
                      IGalleryRepository galleryRepository,
                      IPhotoRepository photoRepository) {
        this.userRepository = userRepository;
        this.galleryRepository = galleryRepository;
        this.photoRepository = photoRepository;
    }
}

