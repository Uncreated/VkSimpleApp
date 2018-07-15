package com.uncreated.vksimpleapp;

import com.uncreated.vksimpleapp.di.DaggerTestMainPresenterComponent;
import com.uncreated.vksimpleapp.di.TestMainPresenterComponent;
import com.uncreated.vksimpleapp.di.modules.AppModule;
import com.uncreated.vksimpleapp.di.modules.EventBusModule;
import com.uncreated.vksimpleapp.model.entity.realm.RealmUser;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.eventbus.BitmapEvents;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;
import com.uncreated.vksimpleapp.presenter.main.MainPresenter;
import com.uncreated.vksimpleapp.view.main.MainView;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class MainPresenterTest {

    private MainPresenter mainPresenter;

    @Mock
    private EventBus eventBus;

    @Mock
    private MainView mainView;

    private Scheduler scheduler = new TestScheduler();

    private Disposable userDisposable;
    private Disposable authDisposable;
    private Disposable galleryDisposable;
    private Disposable themeIdDisposable;

    private ArgumentCaptor<Consumer<User>> userCaptor;

    @SuppressWarnings("unchecked")
    private ArgumentCaptor<Consumer<Auth>> authCaptor;

    @SuppressWarnings("unchecked")
    private ArgumentCaptor<Consumer<Gallery>> galleryCaptor;

    @SuppressWarnings("unchecked")
    private ArgumentCaptor<Consumer<Integer>> themeIdCaptor;

    private ArgumentCaptor<Scheduler> schedulerCaptor;

    @BeforeClass
    public static void beforeClass() {

    }

    @AfterClass
    public static void afterClass() {

    }

    @Before
    public void before() {
        eventBus = Mockito.mock(EventBus.class);

        MockitoAnnotations.initMocks(this);

        userDisposable = Mockito.mock(Disposable.class);
        authDisposable = Mockito.mock(Disposable.class);
        galleryDisposable = Mockito.mock(Disposable.class);
        themeIdDisposable = Mockito.mock(Disposable.class);


        TestMainPresenterComponent component = DaggerTestMainPresenterComponent.builder()
                .appModule(new AppModule(null) {
                    @Override
                    public Scheduler mainThreadScheduler() {
                        return scheduler;
                    }
                })
                .eventBusModule(new EventBusModule() {
                    @Override
                    public EventBus eventBus(BitmapEvents thumbnailEvents, BitmapEvents originalEvents) {
                        return eventBus;
                    }
                }).build();

        mainPresenter = Mockito.spy(new MainPresenter());
        component.inject(mainPresenter);

        initCaptors();

        Mockito.when(eventBus.userSubscribe(userCaptor.capture(), schedulerCaptor.capture()))
                .thenReturn(userDisposable);
        Mockito.when(eventBus.authSubscribe(authCaptor.capture(), schedulerCaptor.capture()))
                .thenReturn(authDisposable);
        Mockito.when(eventBus.gallerySubscribe(galleryCaptor.capture(), schedulerCaptor.capture()))
                .thenReturn(galleryDisposable);
        Mockito.when(eventBus.themeIdSubscribe(themeIdCaptor.capture(), schedulerCaptor.capture()))
                .thenReturn(themeIdDisposable);
    }

    @SuppressWarnings("unchecked")
    private void initCaptors() {
        userCaptor = ArgumentCaptor.forClass(Consumer.class);
        authCaptor = ArgumentCaptor.forClass(Consumer.class);
        galleryCaptor = ArgumentCaptor.forClass(Consumer.class);
        themeIdCaptor = ArgumentCaptor.forClass(Consumer.class);
        schedulerCaptor = ArgumentCaptor.forClass(Scheduler.class);
    }

    @After
    public void after() {

    }

    @Test
    public void onFirstViewAttach() throws Exception {
        //prepare
        User user = new User(new RealmUser());

        Auth authValid = new Auth("", "", 0L);
        Auth authNotValid = Auth.AuthNotValid();
        Auth authLogout = Auth.AuthLogout();

        int gallerySize = 17239;
        Gallery gallery = Mockito.mock(Gallery.class);
        Mockito.when(gallery.getCurrentSize()).thenReturn(gallerySize);

        Integer themeId = 1;

        //run
        mainPresenter.attachView(mainView);

        userCaptor.getValue().accept(user);

        authCaptor.getValue().accept(authValid);
        authCaptor.getValue().accept(authNotValid);
        authCaptor.getValue().accept(authLogout);
        galleryCaptor.getValue().accept(gallery);

        themeIdCaptor.getValue().accept(themeId);

        //verify
        assertEquals(schedulerCaptor.getValue(), this.scheduler);
        for (Scheduler scheduler : schedulerCaptor.getAllValues()) {
            assertEquals(scheduler, this.scheduler);
        }

        Mockito.verify(eventBus, Mockito.times(1)).userSubscribe(any(), any());
        Mockito.verify(eventBus, Mockito.times(1)).authSubscribe(any(), any());
        Mockito.verify(eventBus, Mockito.times(1)).gallerySubscribe(any(), any());
        Mockito.verify(eventBus, Mockito.times(1)).themeIdSubscribe(any(), any());

        Mockito.verify(mainView, Mockito.times(1)).setUser(user);
        Mockito.verify(mainView, Mockito.times(2)).goAuth();
        Mockito.verify(mainView, Mockito.times(1)).setGallerySize(gallerySize);
        Mockito.verify(mainView, Mockito.times(1)).changeTheme(themeId);

    }

    @Test
    public void onLogout() {
        Auth authLogout = Auth.AuthLogout();

        mainPresenter.onLogout();

        Mockito.verify(eventBus, Mockito.times(1)).authPost(authLogout);
    }

    @Test
    public void onDestroy() {
        mainPresenter.attachView(mainView);
        mainPresenter.onDestroy();

        Mockito.verify(userDisposable, Mockito.times(1)).dispose();
        Mockito.verify(authDisposable, Mockito.times(1)).dispose();
        Mockito.verify(galleryDisposable, Mockito.times(1)).dispose();
        Mockito.verify(themeIdDisposable, Mockito.times(1)).dispose();
    }
}
