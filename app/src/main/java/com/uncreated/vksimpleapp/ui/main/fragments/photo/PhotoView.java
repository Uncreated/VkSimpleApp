package com.uncreated.vksimpleapp.ui.main.fragments.photo;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.uncreated.vksimpleapp.model.entity.events.BitmapIndex;

public interface PhotoView extends MvpView {

    void setGallerySize(int size);

    void showError(String error);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void updatePhoto(BitmapIndex bitmapIndex);
}
