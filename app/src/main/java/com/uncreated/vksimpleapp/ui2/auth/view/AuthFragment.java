package com.uncreated.vksimpleapp.ui2.auth.view;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.databinding.FragmentAuthBinding;
import com.uncreated.vksimpleapp.ui2.MainNavigationCallback;
import com.uncreated.vksimpleapp.ui2.auth.viewmodel.AuthViewModel;
import com.uncreated.vksimpleapp.ui2.auth.viewmodel.AuthWebViewModel;

public class AuthFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAuthBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth,
                container, false);

        MainNavigationCallback navigationCallback = ((MainNavigationCallback) getActivity());
        if (navigationCallback == null) {
            throw new RuntimeException("Parent activity must implements " +
                    MainNavigationCallback.class.getName());
        }

        initAuthResult();
        initWebView(binding);

        return binding.getRoot();
    }

    private void initAuthResult() {
        AuthViewModel authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        authViewModel.getAuthSuccessfulLiveData()
                .observe(this, successful -> ((MainNavigationCallback) getActivity()).goGallery());
    }

    private void initWebView(FragmentAuthBinding binding) {
        AuthWebViewModel viewModel = ViewModelProviders.of(this).get(AuthWebViewModel.class);

        binding.wvAuth.setWebViewClient(viewModel.getWebViewClient());

        viewModel.getUrlLiveData()
                .observe(this, url -> {
                    binding.wvAuth.clearCache(true);
                    binding.wvAuth.clearHistory();
                    binding.wvAuth.loadUrl(url);
                });
    }
}
