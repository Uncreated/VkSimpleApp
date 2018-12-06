package com.uncreated.vksimpleapp.ui.main.fragments.photo.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.uncreated.vksimpleapp.model.entity.vk.PhotoInfo;
import com.uncreated.vksimpleapp.ui.main.fragments.photo.page.PageFragment;

import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private List<PhotoInfo> items;

    PagerAdapter(FragmentManager fm, List<PhotoInfo> items) {
        super(fm);
        this.items = items;
    }

    @Override
    public int getCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    public void setItems(List<PhotoInfo> items) {
        if (this.items != items) {
            this.items = items;

            notifyDataSetChanged();//TODO:check
        }
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(items.get(position));
    }
}
