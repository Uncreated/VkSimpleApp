package com.uncreated.vksimpleapp.model.repository;

import android.graphics.Bitmap;

import com.uncreated.vksimpleapp.model.entity.User;
import com.uncreated.vksimpleapp.model.repository.photo.IPhotoRepository;
import com.uncreated.vksimpleapp.model.repository.user.IUserRepository;

import io.reactivex.Observable;

public class Repository {

    private IUserRepository userRepository;
    private IPhotoRepository photoRepository;

    public Repository(IUserRepository userRepository, IPhotoRepository photoRepository) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
    }

    public Observable<User> getUser(String userId) {
        return userRepository.get(userId);
    }

    public Observable<Bitmap> getPhoto(String url) {
        return photoRepository.get(url);
    }
}
