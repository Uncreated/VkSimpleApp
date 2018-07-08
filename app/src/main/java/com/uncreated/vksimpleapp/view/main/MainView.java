package com.uncreated.vksimpleapp.view.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.uncreated.vksimpleapp.model.entity.vk.User;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = "loading")
    void showLoading();

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = "loading")
    void hideLoading();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void goAuth();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void goPhoto(int index);

    @StateStrategyType(SkipStrategy.class)
    void showError(String error);

    void setUser(User user);

    void setGallery(int size);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void updateThumbnail(int index);
}
