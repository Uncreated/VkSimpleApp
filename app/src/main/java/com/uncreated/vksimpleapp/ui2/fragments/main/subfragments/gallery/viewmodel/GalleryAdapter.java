package com.uncreated.vksimpleapp.ui2.fragments.main.subfragments.gallery.viewmodel;


import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.model2.entity.vk.PhotoInfo;
import com.uncreated.vksimpleapp.model2.repository.photo.PhotoParameters;
import com.uncreated.vksimpleapp.model2.repository.photo.PhotoRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.PhotoViewHolder> {

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    @Inject
    PhotoRepository photoRepository;

    private int itemSize;
    private int margin;

    private List<PhotoInfo> items = new ArrayList<>();
    private GalleryAdapter.OnItemClickListener clickListener;

    public GalleryAdapter(int itemSize, int margin,
                          GalleryAdapter.OnItemClickListener clickListener) {
        App.getApp().getAppComponent().inject(this);

        this.itemSize = itemSize;
        this.margin = margin;
        this.clickListener = clickListener;
    }

    public void setItems(List<PhotoInfo> items) {
        if (this.items != items) {
            int rangeStart = this.items != null ? this.items.size() : 0;

            this.items = items;

            notifyItemRangeChanged(rangeStart, items.size() - rangeStart);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public GalleryAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);
        GalleryAdapter.PhotoViewHolder holder = new GalleryAdapter.PhotoViewHolder(view);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(itemSize, itemSize);
        layoutParams.setMargins(margin, margin, margin, margin);
        holder.cardView.setLayoutParams(layoutParams);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.PhotoViewHolder holder, int position) {
        holder.reload(items.get(position));
        holder.cardView.setOnClickListener(v -> clickListener.onItemClicked(position));
    }

    public interface OnItemClickListener {
        void onItemClicked(int index);
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageViewPhoto;

        private Disposable disposable;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.fl_container);
            imageViewPhoto = itemView.findViewById(R.id.iv_photo);
        }

        void reload(@NonNull PhotoInfo photoInfo) {
            if (disposable != null) {
                disposable.dispose();
                disposable = null;
            }

            imageViewPhoto.setImageBitmap(null);

            PhotoParameters parameters = new PhotoParameters(cardView.getContext(), photoInfo.getThumbnailUrl());
            disposable = photoRepository.load(parameters)
                    .observeOn(mainThreadScheduler)
                    .subscribe(imageViewPhoto::setImageBitmap, Timber::e);
        }
    }
}