package com.uncreated.vksimpleapp.view.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(SingleStateStrategy.class)
public interface MainView extends MvpView {

    void showLoading();

    void hideLoading();

    void goAuth();

    void setUser(String name);

    void setUserAvatar();

    @StateStrategyType(SkipStrategy.class)
    void showError(String error);
}
