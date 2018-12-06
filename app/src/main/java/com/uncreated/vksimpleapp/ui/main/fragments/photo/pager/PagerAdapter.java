package com.uncreated.vksimpleapp.ui.main.fragments.photo.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.ui.main.fragments.photo.page.PageFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private Gallery gallery;

    PagerAdapter(FragmentManager fm, Gallery gallery) {
        super(fm);
        this.gallery = gallery;
    }

    @Override
    public int getCount() {
        return gallery.getCurrentSize();
    }

    public void setGallery(Gallery gallery) {
        if (this.gallery != gallery) {
            this.gallery = gallery;

            notifyDataSetChanged();//TODO:check
        }
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(gallery.getItems().get(position));
    }
}
