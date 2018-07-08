package com.uncreated.vksimpleapp.view.photo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryCache;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageFragment extends Fragment {

    @Inject
    EventBus eventBus;
    @Named("thumbnail")
    @Inject
    GalleryCache thumbnailsCache;
    @Named("original")
    @Inject
    GalleryCache originalsCache;
    @BindView(R.id.iv_photo)
    ImageView imageView;
    private int index;

    public PageFragment() {
        App.getApp().getAppComponent().inject(this);
    }

    public static PageFragment newInstance(App app, int index) {
        PageFragment pageFragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        index = getArguments().getInt("index");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_page, container, false);

        ButterKnife.bind(this, view);

        Bitmap bitmap = originalsCache.getBitmap(index);
        if (bitmap == null) {
            bitmap = thumbnailsCache.getBitmap(index);

            eventBus.getOriginalEvents()
                    .getIndexSubject()
                    .onNext(index);
            Toast.makeText(getContext(), "onNext: " + index, Toast.LENGTH_SHORT).show();
        }
        imageView.setImageBitmap(bitmap);


        return view;
    }

    public void update() {
        Bitmap bitmap = originalsCache.getBitmap(index);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

}
