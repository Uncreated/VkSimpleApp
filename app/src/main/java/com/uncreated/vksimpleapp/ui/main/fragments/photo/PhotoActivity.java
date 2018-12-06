package com.uncreated.vksimpleapp.ui.main.fragments.photo;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.databinding.ActivityPhotoBinding;
import com.uncreated.vksimpleapp.model.entity.events.IndexedBitmap;

import javax.inject.Inject;
import javax.inject.Named;

public class PhotoActivity extends MvpAppCompatActivity {

    private PhotoViewModel photoViewModel;

    private ActivityPhotoBinding dataBinding;

    @Named("keyPhotoIndex")
    @Inject
    String keyPhotoIndex;

    private PagerAdapter pagerAdapter;

    public PhotoActivity() {
        App.getApp().getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_photo);

        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);

        int index = getIntent().getIntExtra(keyPhotoIndex, 0);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), index + 1);
        dataBinding.vpPhotos.setAdapter(pagerAdapter);
        dataBinding.vpPhotos.setCurrentItem(index);

        photoViewModel.getBitmapIndexLiveData()
                .observe(this, bitmapIndex -> updatePhoto(null));
    }

    public void setGallerySize(int size) {
        pagerAdapter.setCount(size);
        pagerAdapter.notifyDataSetChanged();
    }

    public void updatePhoto(IndexedBitmap indexedBitmap) {
        pagerAdapter.notifyDataSetChanged();
    }
}
