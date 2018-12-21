package com.uncreated.vksimpleapp.ui.main.fragments.gallery;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.PhotoInfo;
import com.uncreated.vksimpleapp.model.repository.gallery.GalleryRepository;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.disposables.CompositeDisposable;

public class GalleryViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    //@Inject
    GalleryRepository galleryRepository;

    private LiveData<List<PhotoInfo>> photoInfoListLiveData;

    public GalleryViewModel() {
        //App.getApp().getAppComponent().inject(this);

        photoInfoListLiveData = Transformations.map(
                LiveDataReactiveStreams.fromPublisher(galleryRepository.getGalleryObservable()
                        .toFlowable(BackpressureStrategy.LATEST)),
                Gallery::getItems);
    }

    public LiveData<List<PhotoInfo>> getPhotoInfoListLiveData() {
        return photoInfoListLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
