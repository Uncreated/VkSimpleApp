package com.uncreated.vksimpleapp.view.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.uncreated.vksimpleapp.model.entity.vk.User;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void goAuth();

    @StateStrategyType(SkipStrategy.class)
    void showError(String error);

    void setUser(User user);

    void setGallerySize(int size);

    void changeTheme(Integer themeId);
}
