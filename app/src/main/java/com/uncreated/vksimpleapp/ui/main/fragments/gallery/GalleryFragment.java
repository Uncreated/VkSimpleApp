package com.uncreated.vksimpleapp.ui.main.fragments.gallery;

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
import com.uncreated.vksimpleapp.databinding.FragmentGalleryBinding;
import com.uncreated.vksimpleapp.ui.main.fragments.photo.activity.PhotoActivity;

import javax.inject.Inject;
import javax.inject.Named;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    @Named("keyPhotoIndex")
    @Inject
    String keyPhotoIndex;

    private GalleryAdapter galleryAdapter;
    private FragmentGalleryBinding binding;

    public GalleryFragment() {
        App.getApp().getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery,
                container, false);

        float minSize = container.getResources().getDimension(R.dimen.thumbnail_min_size);
        float margin = container.getResources().getDimension(R.dimen.default_margin);

        ((SimpleItemAnimator) binding.rvGallery.getItemAnimator()).setSupportsChangeAnimations(false);
        AutoGridLayoutManager layoutManager = new AutoGridLayoutManager(container.getContext(),
                minSize, margin);
        binding.rvGallery.setLayoutManager(layoutManager);

        galleryAdapter = new GalleryAdapter(0, layoutManager.getItemSize(), (int) margin);
        binding.rvGallery.setAdapter(galleryAdapter);

        GalleryViewModel viewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        viewModel.getGalleryLiveData()
                .observe(this, gallery -> {
                    if (gallery != null) {
                        if (gallery.getCurrentSize() == gallery.getTotalCount()) {
                            hideLoading();
                        }
                    }
                });

        showLoading();

        return binding.getRoot();
    }

    public void setGallery(int size) {
        galleryAdapter.setPhotosCount(size);
        galleryAdapter.notifyDataSetChanged();
    }

    public void goPhoto(int index) {
        Intent intent = new Intent(getContext(), PhotoActivity.class);
        intent.putExtra(keyPhotoIndex, index);
        startActivity(intent);
    }

    public void updateThumbnail(int index) {
        galleryAdapter.notifyItemChanged(index);
    }

    public void showLoading() {
        binding.setRefreshing(true);
    }

    public void hideLoading() {
        binding.setRefreshing(false);
    }
}
