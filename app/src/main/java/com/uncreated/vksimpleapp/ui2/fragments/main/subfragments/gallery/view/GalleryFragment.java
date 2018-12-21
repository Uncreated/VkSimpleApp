package com.uncreated.vksimpleapp.ui2.fragments.main.subfragments.gallery.view;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.databinding.FragmentGallery2Binding;
import com.uncreated.vksimpleapp.model.repository.photo.PhotoRepository;
import com.uncreated.vksimpleapp.ui.main.fragments.gallery.AutoGridLayoutManager;
import com.uncreated.vksimpleapp.ui.main.fragments.gallery.GalleryAdapter;
import com.uncreated.vksimpleapp.ui.main.fragments.photo.activity.PhotoActivity;
import com.uncreated.vksimpleapp.ui2.fragments.main.subfragments.gallery.viewmodel.GalleryViewModel;

import javax.inject.Inject;

public class GalleryFragment extends Fragment {

    @Inject
    PhotoRepository photoRepository;

    public GalleryFragment() {
        App.getApp().getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentGallery2Binding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_gallery2, container, false);

        float minSize = container.getResources().getDimension(R.dimen.thumbnail_min_size);
        float margin = container.getResources().getDimension(R.dimen.default_margin);

        ((SimpleItemAnimator) binding.rvGallery.getItemAnimator()).setSupportsChangeAnimations(false);
        AutoGridLayoutManager layoutManager = new AutoGridLayoutManager(container.getContext(),
                minSize, margin);
        binding.rvGallery.setLayoutManager(layoutManager);

        GalleryViewModel viewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);

        GalleryAdapter galleryAdapter = new GalleryAdapter(photoRepository,
                layoutManager.getItemSize(), (int) margin,
                viewModel.getGalleryLiveData().getValue().getItems(), this::goPhoto);
        binding.rvGallery.setAdapter(galleryAdapter);

        viewModel.getGalleryLiveData()
                .observe(this, gallery -> galleryAdapter.setItems(gallery.getItems()));

        return binding.getRoot();
    }

    public void goPhoto(int index) {
        Intent intent = new Intent(getContext(), PhotoActivity.class);
        PhotoActivity.putArgs(intent, index);
        startActivity(intent);
    }
}
