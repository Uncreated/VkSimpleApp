package com.uncreated.vksimpleapp.ui2.modelview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model2.entity.vk.Auth;
import com.uncreated.vksimpleapp.model2.repository.auth.AuthRepository;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;

public class MainViewModel extends ViewModel {

    @Inject
    AuthRepository authRepository;
    private LiveData<Boolean> isAuthValidLiveData;

    public MainViewModel() {
        App.getApp().getAppComponent().inject(this);

        isAuthValidLiveData = Transformations.map(
                LiveDataReactiveStreams.fromPublisher(authRepository.getAuthObservable()
                        .toFlowable(BackpressureStrategy.LATEST)),
                Auth::isValid);
    }

    public LiveData<Boolean> getIsAuthValidLiveData() {
        return isAuthValidLiveData;
    }
}
