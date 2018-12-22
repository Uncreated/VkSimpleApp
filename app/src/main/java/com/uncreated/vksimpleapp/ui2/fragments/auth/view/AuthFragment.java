package com.uncreated.vksimpleapp.ui2.fragments.auth.view;


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
import com.uncreated.vksimpleapp.ui2.fragments.auth.viewmodel.AuthViewModel;

public class AuthFragment extends Fragment {
    private FragmentAuthBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth,
                container, false);

        initViewModel();

        return binding.getRoot();
    }

    private void initViewModel() {
        AuthViewModel viewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        binding.wvAuth.setWebViewClient(viewModel.getWebViewClient());

        viewModel.getUrlLiveData()
                .observe(this, this::goAuth);
    }

    private void goAuth(String url) {
        //binding.wvAuth.clearCache(true);
        //binding.wvAuth.clearHistory();
        binding.wvAuth.loadUrl(url);
    }
}
