package com.uncreated.vksimpleapp.view.photo;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.uncreated.vksimpleapp.App;

public class PagerAdapter extends FragmentPagerAdapter {

    private int count;

    private App app;

    PagerAdapter(App app, FragmentManager fm, int count) {
        super(fm);
        this.app = app;
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(app, position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        PageFragment pageFragment = (PageFragment) object;
        pageFragment.update();

        return super.getItemPosition(object);
    }
}
