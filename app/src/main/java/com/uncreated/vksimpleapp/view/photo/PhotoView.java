package com.uncreated.vksimpleapp.view.photo;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface PhotoView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showPhoto(String string);
}
