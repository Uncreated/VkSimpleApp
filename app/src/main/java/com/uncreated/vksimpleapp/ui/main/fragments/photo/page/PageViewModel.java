package com.uncreated.vksimpleapp.ui.main.fragments.photo.page;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.repository.photo.PhotoRepository;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class PageViewModel extends ViewModel {

    private final Disposable origDisposable;
    private final Disposable thumbDisposable;
    @Inject
    PhotoRepository photoRepository;
    private MutableLiveData<Bitmap> imageLiveData = new MutableLiveData<>();

    PageViewModel(String thumbnailImageUrl, String originalImageUrl) {
        App.getApp().getAppComponent().inject(this);

        thumbDisposable = photoRepository.getBitmapObservable(thumbnailImageUrl)
                .subscribe(bitmap -> imageLiveData.postValue(bitmap));

        origDisposable = photoRepository.getBitmapObservable(originalImageUrl)
                .subscribe(bitmap -> {
                    thumbDisposable.dispose();
                    imageLiveData.postValue(bitmap);
                });
    }

    MutableLiveData<Bitmap> getImageLiveData() {
        return imageLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        thumbDisposable.dispose();
        origDisposable.dispose();
    }
}
