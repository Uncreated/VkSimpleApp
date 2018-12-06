package com.uncreated.vksimpleapp.ui.main.fragments.photo.page;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.databinding.PhotoPageBinding;
import com.uncreated.vksimpleapp.model.entity.vk.PhotoInfo;

public class PageFragment extends Fragment {

    private static final String KEY_PHOTO_ORIG_URL = "keyPhotoOriginalUrl";
    private static final String KEY_PHOTO_THUMB_URL = "keyPhotoThumbnailUrl";

    public PageFragment() {
        App.getApp().getAppComponent().inject(this);
    }

    public static PageFragment newInstance(PhotoInfo photoInfo) {
        PageFragment pageFragment = new PageFragment();

        Bundle bundle = new Bundle();
        bundle.putString(KEY_PHOTO_ORIG_URL, photoInfo.getOriginalUrl());
        bundle.putString(KEY_PHOTO_THUMB_URL, photoInfo.getThumbnailUrl());
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PhotoPageBinding binding = DataBindingUtil.inflate(inflater, R.layout.photo_page,
                container, false);

        String origUrl = getArguments().getString(KEY_PHOTO_ORIG_URL);
        String thumbUrl = getArguments().getString(KEY_PHOTO_THUMB_URL);

        PageViewModel viewModel = ViewModelProviders.of(this,
                new PageViewModelFactory(origUrl, thumbUrl))
                .get(PageViewModel.class);
        viewModel.getImageLiveData()
                .observe(this, binding::setImage);

        return binding.getRoot();
    }
}
