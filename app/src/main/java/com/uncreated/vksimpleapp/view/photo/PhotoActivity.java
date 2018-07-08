package com.uncreated.vksimpleapp.view.photo;

import android.os.Bundle;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.repository.BitmapIndex;
import com.uncreated.vksimpleapp.presenter.PhotoPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends MvpAppCompatActivity implements PhotoView {

    @InjectPresenter
    PhotoPresenter photoPresenter;

    @Named("keyPhotoIndex")
    @Inject
    String keyPhotoIndex;

    @Inject
    App app;

    @BindView(R.id.iv_photo)
    ImageView imageViewPhoto;

    @Inject
    EventBus eventBus;

    public PhotoActivity() {
        App.getApp().getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ButterKnife.bind(this);

        eventBus.getOriginalEvents()
                .getIndexSubject()
                .onNext(getIntent().getIntExtra(keyPhotoIndex, 0));//TODO: move to adapter
    }

    @ProvidePresenter
    public PhotoPresenter providePhotoPresenter() {
        PhotoPresenter photoPresenter = new PhotoPresenter();
        app.getAppComponent().inject(photoPresenter);
        return photoPresenter;
    }

    @Override
    public void showPhoto(BitmapIndex bitmapIndex) {
        imageViewPhoto.setImageBitmap(bitmapIndex.getBitmap());
    }
}
