package com.uncreated.vksimpleapp.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;
import com.uncreated.vksimpleapp.model.repository.settings.ISettingsRepository;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Scheduler;

public class MainViewModel extends ViewModel {

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    @Inject
    EventBus eventBus;

    @Inject
    ISettingsRepository settingsRepository;

    @Inject
    IAuthRepository authRepository;

    private LiveData<User> userLiveData;
    private LiveData<Integer> gallerySizeLiveData;
    private LiveData<Auth> authLiveData;
    private LiveData<Integer> themeIdLiveData;

    public MainViewModel() {
        App.getApp().getAppComponent().inject(this);

        userLiveData = LiveDataReactiveStreams.fromPublisher(eventBus.getUserPublisher());
        gallerySizeLiveData = Transformations.map(
                LiveDataReactiveStreams.fromPublisher(eventBus.getGalleryPublisher()),
                Gallery::getCurrentSize);
        authLiveData = LiveDataReactiveStreams.fromPublisher(authRepository.getAuthObservable()
                .toFlowable(BackpressureStrategy.LATEST));
        themeIdLiveData = LiveDataReactiveStreams.fromPublisher(eventBus.getThemeIdPublisher());
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Integer> getGallerySizeLiveData() {
        return gallerySizeLiveData;
    }

    public LiveData<Auth> getAuthLiveData() {
        return authLiveData;
    }

    public LiveData<Integer> getThemeIdLiveData() {
        return themeIdLiveData;
    }

    public void onLogout() {
        //eventBus.authPost(Auth.AuthLogout());TODO:
    }

    public int getDefaultThemeId() {
        return settingsRepository.getDefaultThemeId();
    }
}
