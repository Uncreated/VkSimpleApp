package com.uncreated.vksimpleapp.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryRepository;
import com.uncreated.vksimpleapp.model.repository.settings.ISettingsRepository;
import com.uncreated.vksimpleapp.model.repository.user.UserRepository;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;

public class MainViewModel extends ViewModel {
    @Inject
    ISettingsRepository settingsRepository;

    @Inject
    IAuthRepository authRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    GalleryRepository galleryRepository;

    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> gallerySizeLiveData = new MutableLiveData<>();
    private LiveData<Auth> authLiveData;
    //TODO:change to single
    private LiveData<Object> themeChangeLiveData;

    public MainViewModel() {
        App.getApp().getAppComponent().inject(this);

        //TODO:handle disposables
        authRepository.getAuthObservable()
                .subscribe(this::onAuth);

        authLiveData = LiveDataReactiveStreams.fromPublisher(
                authRepository.getAuthObservable()
                        .toFlowable(BackpressureStrategy.LATEST));

        themeChangeLiveData = LiveDataReactiveStreams.fromPublisher(
                settingsRepository.getThemeChangeSubject()
                        .toFlowable(BackpressureStrategy.MISSING));
    }

    @NonNull
    private void onAuth(Auth auth) {
        userRepository.getUserObservable(auth.getUserId())
                .subscribe(user -> userLiveData.postValue(user));
        galleryRepository.setUserId(auth.getUserId());
        galleryRepository.getGalleryObservable()
                .subscribe(gallery -> gallerySizeLiveData.postValue(gallery.getCurrentSize()));
    }

    LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Integer> getGallerySizeLiveData() {
        return gallerySizeLiveData;
    }

    LiveData<Auth> getAuthLiveData() {
        return authLiveData;
    }

    LiveData<Object> getThemeChangeLiveData() {
        return themeChangeLiveData;
    }

    void onLogout() {
        authRepository.setAuth(Auth.AuthNotValid());
    }

    int getDefaultThemeId() {
        return settingsRepository.getDefaultThemeId();
    }
}
