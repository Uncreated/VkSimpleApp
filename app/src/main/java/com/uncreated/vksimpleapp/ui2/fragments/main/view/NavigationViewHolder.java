package com.uncreated.vksimpleapp.ui2.fragments.main.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uncreated.vksimpleapp.R;

public class NavigationViewHolder {

    private TextView textViewName;
    private ImageView imageViewAvatar;

    public NavigationViewHolder(View headerView) {
        textViewName = headerView.findViewById(R.id.tv_name);
        imageViewAvatar = headerView.findViewById(R.id.iv_avatar);
    }

    public TextView getTextViewName() {
        return textViewName;
    }

    public ImageView getImageViewAvatar() {
        return imageViewAvatar;
    }
}
