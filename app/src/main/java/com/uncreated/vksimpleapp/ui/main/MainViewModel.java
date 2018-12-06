package com.uncreated.vksimpleapp.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryRepository;
import com.uncreated.vksimpleapp.model.repository.settings.ISettingsRepository;
import com.uncreated.vksimpleapp.model.repository.user.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Scheduler;

public class MainViewModel extends ViewModel {

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    @Inject
    ISettingsRepository settingsRepository;

    @Inject
    IAuthRepository authRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    GalleryRepository galleryRepository;

    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<Gallery> galleryLiveData = new MutableLiveData<>();
    private LiveData<Auth> authLiveData;
    //TODO:change to single
    private LiveData<Object> themeChangeLiveData;

    public MainViewModel() {
        App.getApp().getAppComponent().inject(this);

        //TODO:handle disposables
        authRepository.getAuthObservable()
                .subscribe(auth -> {
                    userRepository.getUserObservable(auth.getUserId())
                            .subscribe(user -> {
                                userLiveData.postValue(user);
                            });
                    galleryRepository.getGalleryObservable(auth.getUserId())
                            .subscribe(gallery -> galleryLiveData.postValue(gallery));
                });

        authLiveData = LiveDataReactiveStreams.fromPublisher(
                authRepository.getAuthObservable()
                        .toFlowable(BackpressureStrategy.LATEST));

        themeChangeLiveData = LiveDataReactiveStreams.fromPublisher(
                settingsRepository.getThemeChangeSubject()
                        .toFlowable(BackpressureStrategy.MISSING));
    }

    LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Gallery> getGalleryLiveData() {
        return galleryLiveData;
    }

    LiveData<Auth> getAuthLiveData() {
        return authLiveData;
    }

    LiveData<Object> getThemeChangeLiveData() {
        return themeChangeLiveData;
    }

    void onLogout() {
        authRepository.setAuth(Auth.AuthLogout());
    }

    int getDefaultThemeId() {
        return settingsRepository.getDefaultThemeId();
    }
}
