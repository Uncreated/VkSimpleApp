package com.uncreated.vksimpleapp.ui.main.fragments.photo.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.ui.main.fragments.photo.pager.PagerFragment;

public class PhotoActivity extends AppCompatActivity {

    private static final String KEY_PHOTO_INDEX = "keyPhotoIndex";

    public static void putArgs(Intent intent, int index) {
        intent.putExtra(KEY_PHOTO_INDEX, index);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ViewModelProviders.of(this).get(PhotoViewModel.class);//just create

        int startIndex = getIntent().getIntExtra(KEY_PHOTO_INDEX, 0);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PagerFragment.getIntance(startIndex))
                .commit();
    }
}
