package com.uncreated.vksimpleapp.view.main;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
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

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    @Named("thumbnail")
    @Inject
    GalleryPhotoCache galleryPhotoCache;

    @Inject
    EventBus eventBus;

    private int photosCount;

    PhotosAdapter(int photosCount) {
        this.photosCount = photosCount;
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
        return new PhotoViewHolder(view);
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
        holder.frameLayout.setOnClickListener(listener);
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        FrameLayout frameLayout;
        ImageView imageViewPhoto;

        PhotoViewHolder(View itemView) {
            super(itemView);

            frameLayout = itemView.findViewById(R.id.fl_container);
            imageViewPhoto = itemView.findViewById(R.id.iv_photo);
        }
    }
}
