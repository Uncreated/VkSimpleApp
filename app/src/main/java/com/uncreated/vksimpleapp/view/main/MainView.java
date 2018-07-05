package com.uncreated.vksimpleapp.view.main;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(SingleStateStrategy.class)
public interface MainView extends MvpView {

    @StateStrategyType(value = SingleStateStrategy.class, tag = "loading")
    void showLoading();

    @StateStrategyType(value = SingleStateStrategy.class, tag = "loading")
    void hideLoading();

    @StateStrategyType(SkipStrategy.class)
    void goAuth();

    @StateStrategyType(SkipStrategy.class)
    void showError(String error);

    void setUserName(String firstName, String lastName);

    void setUserAvatar(Bitmap bitmap);

    void setGallerySize(int size);
}
