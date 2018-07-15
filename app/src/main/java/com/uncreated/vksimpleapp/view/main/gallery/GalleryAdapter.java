package com.uncreated.vksimpleapp.view.main.gallery;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryPhotoCache;

import javax.inject.Inject;
import javax.inject.Named;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.PhotoViewHolder> {

    @Named("thumbnail")
    @Inject
    GalleryPhotoCache galleryPhotoCache;

    @Inject
    EventBus eventBus;

    private int photosCount;
    private int itemSize;
    private int margin;

    GalleryAdapter(int photosCount, int itemSize, int margin) {
        this.photosCount = photosCount;
        this.itemSize = itemSize;
        this.margin = margin;
    }

    public void setPhotosCount(int photosCount) {
        this.photosCount = photosCount;
    }

    @Override
    public int getItemCount() {
        return photosCount;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);
        PhotoViewHolder holder = new PhotoViewHolder(view);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(itemSize, itemSize);
        layoutParams.setMargins(margin, margin, margin, margin);
        holder.cardView.setLayoutParams(layoutParams);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Bitmap bitmap = galleryPhotoCache.getBitmap(position);
        if (bitmap != null) {
            holder.imageViewPhoto.setImageBitmap(bitmap);
        } else {
            holder.imageViewPhoto.setImageBitmap(null);
            eventBus.getThumbnailEvents()
                    .getIndexSubject()
                    .onNext(position);
        }
        View.OnClickListener listener = v -> eventBus.getClickThumbnailSubject().onNext(position);
        holder.cardView.setOnClickListener(listener);
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageViewPhoto;

        PhotoViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.fl_container);
            imageViewPhoto = itemView.findViewById(R.id.iv_photo);
        }
    }
}
