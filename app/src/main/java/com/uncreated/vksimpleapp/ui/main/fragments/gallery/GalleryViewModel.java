package com.uncreated.vksimpleapp.ui.main.fragments.gallery;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryRepository;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

public class GalleryViewModel extends ViewModel {

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    GalleryRepository galleryRepository;

    private LiveData<Gallery> galleryLiveData;

    public GalleryViewModel() {
        App.getApp().getAppComponent().inject(this);

        galleryLiveData = LiveDataReactiveStreams.fromPublisher(galleryRepository.getGalleryObservable()
                .toFlowable(BackpressureStrategy.LATEST));
    }

    public LiveData<Gallery> getGalleryLiveData() {
        return galleryLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
