package com.uncreated.vksimpleapp.view.main.settings;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface SettingsView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setThemeValue(boolean value);
}
