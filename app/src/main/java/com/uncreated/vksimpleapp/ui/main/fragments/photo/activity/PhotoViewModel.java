package com.uncreated.vksimpleapp.ui.main.fragments.photo.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryRepository;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;

public class PhotoViewModel extends ViewModel {

    @Inject
    GalleryRepository galleryRepository;

    private LiveData<Gallery> galleryLiveData;

    public PhotoViewModel() {
        App.getApp().getAppComponent().inject(this);

        galleryLiveData = LiveDataReactiveStreams.fromPublisher(galleryRepository.getGalleryObservable()
                .toFlowable(BackpressureStrategy.LATEST));
    }

    public LiveData<Gallery> getGalleryLiveData() {
        return galleryLiveData;
    }
}
