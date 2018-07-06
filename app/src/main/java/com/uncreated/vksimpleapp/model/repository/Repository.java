package com.uncreated.vksimpleapp.model.repository;

import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.gallery.IGalleryRepository;
import com.uncreated.vksimpleapp.model.repository.user.IUserRepository;

import io.reactivex.Observable;

public class Repository {

    private IUserRepository userRepository;
    private IGalleryRepository galleryRepository;

    private User user;

    public Repository(IUserRepository userRepository,
                      IGalleryRepository galleryRepository) {
        this.userRepository = userRepository;
        this.galleryRepository = galleryRepository;
    }

    public Observable<User> getUser(String userId) {
        return Observable.zip(userRepository.get(userId),
                galleryRepository.get(userId),
                (user, gallery) -> {
                    user.setGallery(gallery);
                    return user;
                })
                .map(user -> {
                    Repository.this.user = user;
                    return user;
                });
    }
}
