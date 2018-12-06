package com.uncreated.vksimpleapp.ui.main.fragments.photo.page;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

class PageViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final String thumbUrl;
    private final String origUrl;

    PageViewModelFactory(String thumbUrl, String origUrl) {
        this.thumbUrl = thumbUrl;
        this.origUrl = origUrl;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PageViewModel.class) {
            PageViewModel viewModel = new PageViewModel();
            viewModel.setUrls(thumbUrl, origUrl);
            return (T) viewModel;
        }
        return null;
    }
}