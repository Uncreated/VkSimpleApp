package com.uncreated.vksimpleapp.view.photo;

import android.os.Bundle;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.squareup.picasso.Picasso;
import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.presenter.PhotoPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends MvpAppCompatActivity implements PhotoView {

    @InjectPresenter
    PhotoPresenter photoPresenter;

    @Named("keyPhotoUrl")
    @Inject
    String keyPhotoUrl;

    @Inject
    App app;

    @BindView(R.id.iv_photo)
    ImageView imageViewPhoto;

    public PhotoActivity() {
        App.getApp().getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ButterKnife.bind(this);
    }

    @ProvidePresenter
    public PhotoPresenter providePhotoPresenter() {
        PhotoPresenter photoPresenter = new PhotoPresenter(getPhotoUrl());
        app.getAppComponent().inject(photoPresenter);
        return photoPresenter;
    }

    private String getPhotoUrl() {
        return getIntent().getStringExtra(keyPhotoUrl);
    }

    @Override
    public void showPhoto(String string) {
        Picasso.get()
                .load(string)
                .into(imageViewPhoto);
    }
}
