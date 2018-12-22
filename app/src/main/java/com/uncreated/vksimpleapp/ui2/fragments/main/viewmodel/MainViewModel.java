package com.uncreated.vksimpleapp.ui2.fragments.main.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model2.entity.vk.Auth;
import com.uncreated.vksimpleapp.model2.repository.auth.AuthRepository;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    @Inject
    AuthRepository authRepository;

    public MainViewModel() {
        App.getApp().getAppComponent().inject(this);


    }


    public void onLogout() {
        authRepository.setAuth(Auth.AuthNotValid());
    }
}
