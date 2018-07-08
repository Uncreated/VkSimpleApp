package com.uncreated.vksimpleapp.view.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.photo.GlideApp;
import com.uncreated.vksimpleapp.presenter.MainPresenter;
import com.uncreated.vksimpleapp.view.AutoGridLayoutManager;
import com.uncreated.vksimpleapp.view.auth.AuthActivity;
import com.uncreated.vksimpleapp.view.photo.PhotoActivity;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private static final int AUTH_CODE = 1;
    private static final int PHOTO_CODE = 2;

    @Inject
    App app;

    @Named("keyPhotoIndex")
    @Inject
    String keyPhotoIndex;

    @InjectPresenter
    MainPresenter mainPresenter;

    @BindView(R.id.tv_name)
    TextView textViewName;

    @BindView(R.id.iv_avatar)
    ImageView imageViewAvatar;

    @BindView(R.id.tv_gallery_size)
    TextView textViewGallerySize;

    @BindView(R.id.rv_photos)
    RecyclerView recyclerViewPhotos;

    private AlertDialog dialog;

    private PhotosAdapter photosAdapter;

    public MainActivity() {
        App.getApp().getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ((SimpleItemAnimator) recyclerViewPhotos.getItemAnimator()).setSupportsChangeAnimations(false);

        photosAdapter = new PhotosAdapter(0);
        app.getAppComponent().inject(photosAdapter);

        recyclerViewPhotos.setLayoutManager(new AutoGridLayoutManager(this, 100));
        recyclerViewPhotos.setAdapter(photosAdapter);
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        MainPresenter mainPresenter = new MainPresenter();
        app.getAppComponent().inject(mainPresenter);
        return mainPresenter;
    }

    @Override
    public void goAuth() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivityForResult(intent, AUTH_CODE);
    }

    @Override
    public void goPhoto(int index) {
        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra(keyPhotoIndex, index);
        startActivityForResult(intent, PHOTO_CODE);
    }

    @Override
    public void setUser(User user) {
        String fullName = user.getFirstName() + " " + user.getLastName();
        textViewName.setText(fullName);

        GlideApp.with(this)
                .load(user.getPhotoUrl())//TODO: move to presenter like observable
                .into(imageViewAvatar);
    }

    @Override
    public void setGallery(int size) {
        textViewGallerySize.setText("GallerySize:" + size);

        photosAdapter.setPhotosCount(size);
        photosAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateThumbnail(int index) {
        photosAdapter.notifyItemChanged(index);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("Loading...")
                    .create();
        }
    }

    @Override
    public void hideLoading() {
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
        }
    }
}
