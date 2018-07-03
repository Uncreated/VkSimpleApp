package com.uncreated.vksimpleapp.view.auth;

import android.webkit.WebViewClient;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(SingleStateStrategy.class)
public interface AuthView extends MvpView {

    void showLoading();

    void hideLoading();

    @StateStrategyType(SkipStrategy.class)
    void go(String url, WebViewClient webViewClient);

    void showError(String error);
}
