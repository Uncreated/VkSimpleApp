package com.uncreated.vksimpleapp.view.photo;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.uncreated.vksimpleapp.App;
import com.uncreated.vksimpleapp.model.repository.photo.ram.GalleryCache;

public class PagerAdapter extends FragmentPagerAdapter {

    private GalleryCache thumbnailsCache;
    private GalleryCache originalsCache;

    private int count;

    private App app;

    public PagerAdapter(App app,
                        FragmentManager fm,
                        GalleryCache thumbnailsCache,
                        GalleryCache originalsCache,
                        int count) {
        super(fm);
        this.app = app;
        this.thumbnailsCache = thumbnailsCache;
        this.originalsCache = originalsCache;
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
