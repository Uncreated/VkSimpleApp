package com.uncreated.vksimpleapp.presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.auth.AuthWebClient;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    @Inject
    AuthWebClient authWebClient;

    @Inject
    EventBus eventBus;

    private MutableLiveData<AuthWebClient> authWebClientLiveData = new MutableLiveData<>();
    private LiveData<Boolean> authSuccessfulLiveData;

    public AuthViewModel() {
        App.getApp().getAppComponent().inject(this);

        authSuccessfulLiveData = Transformations.map(
                LiveDataReactiveStreams.fromPublisher(eventBus.getAuthSubject()),
                Auth::isValid);

        authWebClientLiveData.setValue(authWebClient);
    }

    public LiveData<AuthWebClient> getAuthWebClientLiveData() {
        return authWebClientLiveData;
    }

    public LiveData<Boolean> getAuthSuccessfulLiveData() {
        return authSuccessfulLiveData;
    }
}
