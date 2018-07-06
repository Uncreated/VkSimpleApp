package com.uncreated.vksimpleapp.view.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    private Gallery gallery;

    private PublishSubject<Integer> onClickSubject = PublishSubject.create();
    private PublishSubject<Integer> onLoadSubject = PublishSubject.create();

    public PhotosAdapter(Gallery gallery) {
        this.gallery = gallery;
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
        Picasso.get()
                .load(gallery.getItems().get(position).getThumbnailUrl())
                .into(holder.imageViewPhoto);

        holder.imageViewPhoto.setOnClickListener(v -> onClickSubject.onNext(position));
    }

    public Observable<Integer> getClicks() {
        return onClickSubject;
    }

    public PublishSubject<Integer> getLoads() {
        return onLoadSubject;
    }

    @Override
    public int getItemCount() {
        return gallery.getItems().size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPhoto;

        PhotoViewHolder(View itemView) {
            super(itemView);

            imageViewPhoto = itemView.findViewById(R.id.iv_photo);
        }
    }
}