package com.uncreated.vksimpleapp.ui.auth.web;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.databinding.FragmentAuthWebBinding;

public class AuthWebFragment extends Fragment {

    private FragmentAuthWebBinding binding;

    public AuthWebFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_auth_web, container, false);

        AuthWebViewModel viewModel = ViewModelProviders.of(this).get(AuthWebViewModel.class);

        binding.wvAuth.setWebViewClient(viewModel.getWebViewClient());

        viewModel.getUrlLiveData()
                .observe(this, url -> {
                    binding.wvAuth.clearCache(true);
                    binding.wvAuth.clearHistory();
                    binding.wvAuth.loadUrl(url);
                });

        return binding.getRoot();
    }

}
