package com.uncreated.vksimpleapp.ui.main.fragments.settings;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.ViewModel;

import com.uncreated.vksimpleapp.model.repository.settings.ISettingsRepository;
import com.uncreated.vksimpleapp.model.repository.settings.entities.SettingsValues;

import io.reactivex.BackpressureStrategy;

public class SettingsViewModel extends ViewModel {

    //@Inject
    ISettingsRepository settingsRepository;

    private LiveData<SettingsValues> settingsValuesLiveData;

    public SettingsViewModel() {
        //App.getApp().getAppComponent().inject(this);

        settingsValuesLiveData = LiveDataReactiveStreams.fromPublisher(
                settingsRepository.getSettingsValues().toFlowable(BackpressureStrategy.LATEST));
    }

    public LiveData<SettingsValues> getSettingsValuesLiveData() {
        return settingsValuesLiveData;
    }

    public void setSettingsValues(SettingsValues settingsValues) {
        settingsRepository.setSettingsValues(settingsValues);
    }
}
