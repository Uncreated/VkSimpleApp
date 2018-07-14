package com.uncreated.vksimpleapp.view.main.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.presenter.main.GalleryPresenter;
import com.uncreated.vksimpleapp.view.photo.PhotoActivity;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryFragment extends MvpAppCompatFragment implements GalleryView {

    @Inject
    App app;

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rv_gallery)
    RecyclerView recyclerViewGallery;

    @Named("keyPhotoIndex")
    @Inject
    String keyPhotoIndex;

    @InjectPresenter
    GalleryPresenter galleryPresenter;

    private PhotosAdapter photosAdapter;

    public GalleryFragment() {
        App.getApp().getAppComponent().inject(this);
    }

    @ProvidePresenter
    public GalleryPresenter provideGalleryPresenter() {
        GalleryPresenter galleryPresenter = new GalleryPresenter();
        app.getAppComponent().inject(galleryPresenter);
        return galleryPresenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = container.getContext();

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);

        float minSize = context.getResources().getDimension(R.dimen.thumbnail_min_size);
        float margin = context.getResources().getDimension(R.dimen.default_margin);

        ((SimpleItemAnimator) recyclerViewGallery.getItemAnimator()).setSupportsChangeAnimations(false);
        AutoGridLayoutManager layoutManager = new AutoGridLayoutManager(context, minSize, margin);
        recyclerViewGallery.setLayoutManager(layoutManager);

        photosAdapter = new PhotosAdapter(0, layoutManager.getItemSize(), (int) margin);
        app.getAppComponent().inject(photosAdapter);
        recyclerViewGallery.setAdapter(photosAdapter);

        return view;
    }

    @Override
    public void setGallery(int size) {
        photosAdapter.setPhotosCount(size);
        photosAdapter.notifyDataSetChanged();
    }

    @Override
    public void goPhoto(int index) {
        Intent intent = new Intent(getContext(), PhotoActivity.class);
        intent.putExtra(keyPhotoIndex, index);
        startActivity(intent);
    }

    @Override
    public void updateThumbnail(int index) {
        photosAdapter.notifyItemChanged(index);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
