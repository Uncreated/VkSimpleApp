package com.uncreated.vksimpleapp.ui.auth.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;

public class AuthViewModel extends ViewModel {

    @Inject
    IAuthRepository authRepository;

    private LiveData<Boolean> authSuccessfulLiveData;

    public AuthViewModel() {
        App.getApp().getAppComponent().inject(this);

        authSuccessfulLiveData = Transformations.map(
                LiveDataReactiveStreams.fromPublisher(authRepository.getAuthObservable()
                        .toFlowable(BackpressureStrategy.LATEST)),
                Auth::isValid);
    }

    public LiveData<Boolean> getAuthSuccessfulLiveData() {
        return authSuccessfulLiveData;
    }
}
