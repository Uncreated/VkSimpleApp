package com.uncreated.vksimpleapp.view.photo;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.uncreated.vksimpleapp.model.repository.BitmapIndex;

public interface PhotoView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showPhoto(BitmapIndex bitmapIndex);
}
