package com.uncreated.vksimpleapp.ui.main.fragments.photo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.model.entity.events.BitmapIndex;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;

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

    @BindView(R.id.vp_photos)
    ViewPager viewPager;

    @Inject
    EventBus eventBus;

    private PagerAdapter pagerAdapter;

    public PhotoActivity() {
        App.getApp().getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ButterKnife.bind(this);

        int index = getIntent().getIntExtra(keyPhotoIndex, 0);
        pagerAdapter = new PagerAdapter(app,
                getSupportFragmentManager(),
                index + 1);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(index);
    }

    @Override
    public void setGallerySize(int size) {
        pagerAdapter.setCount(size);
        pagerAdapter.notifyDataSetChanged();
    }

    @ProvidePresenter
    public PhotoPresenter providePhotoPresenter() {
        PhotoPresenter photoPresenter = new PhotoPresenter();
        app.getAppComponent().inject(photoPresenter);
        return photoPresenter;
    }

    @Override
    public void updatePhoto(BitmapIndex bitmapIndex) {
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
    }
}
