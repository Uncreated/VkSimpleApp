package com.uncreated.vksimpleapp.ui2.fragments.main.subfragments.gallery.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.repository.auth.AuthRepository;
import com.uncreated.vksimpleapp.model2.repository.gallery.GalleryRepository;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class GalleryViewModel extends ViewModel {
    @Inject
    GalleryRepository galleryRepository;

    @Inject
    AuthRepository authRepository;

    private MutableLiveData<Gallery> galleryLiveData = new MutableLiveData<>();

    private Disposable disposable;

    public GalleryViewModel() {
        App.getApp().getAppComponent().inject(this);

        disposable = authRepository.getAuthObservable()
                .lastElement()
                .subscribe(auth ->
                        disposable = galleryRepository.loadFromWeb(auth.getUserId())
                                .subscribe(galleryLiveData::setValue));
    }

    public LiveData<Gallery> getGalleryLiveData() {
        return galleryLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposable.dispose();
    }
}