package com.uncreated.vksimpleapp.model.repository.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

public class PhotoRepositoryWeb implements IPhotoRepository {

    private Context context;

    private Picasso picasso;

    public PhotoRepositoryWeb(Context context, Picasso picasso) {
        this.context = context;
        this.picasso = picasso;
    }

    private Target getTarget(Subject<Bitmap> subject) {
        return new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                subject.onNext(bitmap);
                subject.onComplete();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                e.printStackTrace();
                Timber.e(e);
                subject.onError(e);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                //nothing
            }
        };
    }

    @Override
    public Observable<Bitmap> get(String url) {
        Subject<Bitmap> subject = PublishSubject.create();
        Picasso.get()
                .load(url)
                .noPlaceholder()
                .into(getTarget(subject));
        return subject;
    }

    @Override
    public void set(String s, Bitmap bitmap) {
        //nothing
    }
}
