package com.uncreated.vksimpleapp.ui.main.fragments.settings;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uncreated.vksimpleapp.R;
import com.uncreated.vksimpleapp.databinding.FragmentSettingsBinding;
import com.uncreated.vksimpleapp.model.repository.settings.entities.SettingsValues;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding dataBinding;
    private SettingsViewModel settingsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container,
                false);

        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);

        settingsViewModel.getSettingsValuesLiveData()
                .observe(this, settingsValues -> {
                    dataBinding.setValues(settingsValues);
                });

        return dataBinding.getRoot();
    }

    public void onSettingsValuesChanged(SettingsValues settingsValues) {
        settingsViewModel.setSettingsValues(settingsValues);
    }
}
