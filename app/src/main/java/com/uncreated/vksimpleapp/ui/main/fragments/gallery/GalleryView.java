package com.uncreated.vksimpleapp.ui.main.fragments.gallery;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface GalleryView extends MvpView {
    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = "loading")
    void showLoading();

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = "loading")
    void hideLoading();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void updateThumbnail(int index);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void goPhoto(int index);

    void setGallery(int size);
}
