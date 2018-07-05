package com.uncreated.vksimpleapp.model.repository;

import android.graphics.Bitmap;

import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.gallery.IGalleryRepository;
import com.uncreated.vksimpleapp.model.repository.photo.IPhotoRepository;
import com.uncreated.vksimpleapp.model.repository.user.IUserRepository;

import io.reactivex.Observable;

public class Repository {

    private IUserRepository userRepository;
    private IPhotoRepository photoRepository;
    private IGalleryRepository galleryRepository;

    public Repository(IUserRepository userRepository,
                      IPhotoRepository photoRepository,
                      IGalleryRepository galleryRepository) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.galleryRepository = galleryRepository;
    }

    public Observable<User> getUser(String userId) {
        return userRepository.get(userId);
    }

    public Observable<Bitmap> getPhoto(String url) {
        return photoRepository.get(url);
    }

    public Observable<Gallery> getGallery(User user) {
        return galleryRepository.get(user);
    }
}
