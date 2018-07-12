package com.uncreated.vksimpleapp.view.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uncreated.vksimpleapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationViewHolder {
    @BindView(R.id.tv_name)
    TextView textViewName;

    @BindView(R.id.iv_avatar)
    ImageView imageViewAvatar;


    NavigationViewHolder(View headerView) {
        ButterKnife.bind(this, headerView);
    }


    public TextView getTextViewName() {
        return textViewName;
    }

    public ImageView getImageViewAvatar() {
        return imageViewAvatar;
    }
}
