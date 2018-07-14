package com.uncreated.vksimpleapp.presenter.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.settings.ISettingsRepository;
import com.uncreated.vksimpleapp.view.main.MainView;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Named("mainThread")
    @Inject
    Scheduler mainThreadScheduler;

    @Inject
    EventBus eventBus;

    @Inject
    ISettingsRepository settingsRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        compositeDisposable.add(eventBus.getUserSubject()
                .observeOn(mainThreadScheduler)
                .subscribe(user -> getViewState().setUser(user)));

        compositeDisposable.add(eventBus.getGallerySubject()
                .observeOn(mainThreadScheduler)
                .subscribe(gallery -> getViewState().setGallerySize(gallery.getItems().size())));

        compositeDisposable.add(eventBus.getAuthNotValidSubject()
                .observeOn(mainThreadScheduler)
                .subscribe(o -> getViewState().goAuth()));
    }

    public void onLogout() {
        eventBus.getAuthNotValidSubject()
                .onNext(true);
    }
}
