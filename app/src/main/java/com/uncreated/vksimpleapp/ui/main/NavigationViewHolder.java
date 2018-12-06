package com.uncreated.vksimpleapp.ui.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uncreated.vksimpleapp.R;

class NavigationViewHolder {

    private TextView textViewName;
    private ImageView imageViewAvatar;

    NavigationViewHolder(View headerView) {
        textViewName = headerView.findViewById(R.id.tv_name);
        imageViewAvatar = headerView.findViewById(R.id.iv_avatar);
    }

    TextView getTextViewName() {
        return textViewName;
    }

    ImageView getImageViewAvatar() {
        return imageViewAvatar;
    }
}
