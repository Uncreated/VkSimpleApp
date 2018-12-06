package com.uncreated.vksimpleapp.ui.main.fragments.gallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.model.entity.vk.PhotoInfo;
import com.uncreated.vksimpleapp.model.repository.photo.PhotoRepository;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.PhotoViewHolder> {

    private PhotoRepository photoRepository;

    private int itemSize;
    private int margin;

    private List<PhotoInfo> items;
    private OnItemClickListener clickListener;

    GalleryAdapter(PhotoRepository photoRepository, int itemSize, int margin,
                   List<PhotoInfo> items, OnItemClickListener clickListener) {
        this.photoRepository = photoRepository;
        this.itemSize = itemSize;
        this.margin = margin;
        this.items = items;
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
        if (items != null) {
            return items.size();
        }
        return 0;
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
        holder.reload(items.get(position));
        holder.cardView.setOnClickListener(v -> clickListener.onItemClicked(position));
    }

    interface OnItemClickListener {
        void onItemClicked(int index);
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageViewPhoto;

        Disposable disposable;

        PhotoViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.fl_container);
            imageViewPhoto = itemView.findViewById(R.id.iv_photo);
        }

        void reload(PhotoInfo photoInfo) {
            if (disposable != null) {
                disposable.dispose();
                disposable = null;
            }

            imageViewPhoto.setImageBitmap(null);

            disposable = photoRepository.getBitmapObservable(photoInfo.getThumbnailUrl())
                    .subscribe(bitmap -> imageViewPhoto.setImageBitmap(bitmap));
        }
    }
}
