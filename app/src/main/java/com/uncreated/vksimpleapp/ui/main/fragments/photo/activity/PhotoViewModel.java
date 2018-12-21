package com.uncreated.vksimpleapp.ui.main.fragments.photo.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.PhotoInfo;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryRepository;

import java.util.List;

import io.reactivex.BackpressureStrategy;

public class PhotoViewModel extends ViewModel {

    //@Inject
    GalleryRepository galleryRepository;

    private LiveData<List<PhotoInfo>> galleryLiveData;

    public PhotoViewModel() {
        //App.getApp().getAppComponent().inject(this);

        galleryLiveData = Transformations.map(
                LiveDataReactiveStreams.fromPublisher(galleryRepository.getGalleryObservable()
                        .toFlowable(BackpressureStrategy.LATEST)),
                Gallery::getItems);
    }

    public LiveData<List<PhotoInfo>> getGalleryLiveData() {
        return galleryLiveData;
    }
}
