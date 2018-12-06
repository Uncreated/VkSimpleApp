package com.uncreated.vksimpleapp.ui.main.fragments.photo.pager;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.databinding.FragmentPagerBinding;
import com.uncreated.vksimpleapp.ui.main.MainViewModel;

public class PagerFragment extends Fragment {

    public static final String KEY_INDEX = "keyIndex";
    //TODO
    private static int currentIndex = 0;
    private PagerAdapter pagerAdapter;

    public PagerFragment() {
    }

    public static PagerFragment getIntance(int startIndex) {
        PagerFragment pagerFragment = new PagerFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_INDEX, startIndex);

        pagerFragment.setArguments(args);

        return pagerFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPagerBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_pager, container, false);

        MainViewModel viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        pagerAdapter = new PagerAdapter(getChildFragmentManager(),
                viewModel.getGalleryLiveData().getValue());
        binding.vpPhotos.setAdapter(pagerAdapter);
        binding.vpPhotos.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                //none
            }

            @Override
            public void onPageSelected(int i) {
                currentIndex = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                //none
            }
        });

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args != null) {
                currentIndex = args.getInt(KEY_INDEX, currentIndex);
            }
        }
        binding.vpPhotos.setCurrentItem(currentIndex);

        viewModel.getGalleryLiveData()
                .observe(this, gallery -> pagerAdapter.setGallery(gallery));

        return binding.getRoot();
    }

}
