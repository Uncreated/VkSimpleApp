package com.uncreated.vksimpleapp;

import com.uncreated.vksimpleapp.model.entity.responses.RequestError;
import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;
import com.uncreated.vksimpleapp.model.repository.user.UserRepository;
import com.uncreated.vksimpleapp.model.repository.user.UserStorageLoader;
import com.uncreated.vksimpleapp.model.repository.user.UserWebLoader;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;

import static org.mockito.ArgumentMatchers.any;

public class UserRepositoryTest {

    private UserRepository userRepository;

    private EventBus eventBus;
    private UserWebLoader userWebLoader;
    private UserStorageLoader userStorageLoader;
    private ArgumentCaptor<Consumer<Auth>> authConsumer;
    private ArgumentCaptor<Scheduler> schedulerConsumer;
    private Auth authValid;
    private User user;
    private RequestError requestError;
    private RuntimeException runtimeException;

    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        eventBus = Mockito.mock(EventBus.class);
        userWebLoader = Mockito.mock(UserWebLoader.class);
        userStorageLoader = Mockito.mock(UserStorageLoader.class);

        initCaptors();

        Mockito.when(eventBus.authSubscribe(authConsumer.capture(), schedulerConsumer.capture()))
                .thenReturn(null);

        authValid = new Auth("12", "34", 0L);
        user = Mockito.mock(User.class);
        requestError = new RequestError();
        runtimeException = new RuntimeException();
    }

    @SuppressWarnings("unchecked")
    private void initCaptors() {
        authConsumer = ArgumentCaptor.forClass(Consumer.class);
        schedulerConsumer = ArgumentCaptor.forClass(Scheduler.class);
    }

    @Test
    public void subscribe() {
        userRepository = Mockito.spy(new UserRepository(eventBus, userWebLoader, userStorageLoader));

        Mockito.verify(eventBus, Mockito.times(1)).authSubscribe(any(), any());
    }

    @Test
    public void ignoreInvalidAuth() throws Exception {
        Auth authNotValid = Auth.AuthNotValid();
        Auth authLogout = Auth.AuthLogout();

        userRepository = Mockito.spy(new UserRepository(eventBus, userWebLoader, userStorageLoader));
        authConsumer.getValue().accept(authNotValid);
        authConsumer.getValue().accept(authLogout);

        Mockito.verify(userWebLoader, Mockito.times(0)).loadUser(any());
        Mockito.verify(userStorageLoader, Mockito.times(0)).loadUser(any());
    }

    @Test
    public void loadUserWeb() throws Exception {

        Mockito.when(eventBus.authSubscribe(authConsumer.capture(), schedulerConsumer.capture()))
                .thenReturn(null);

        Mockito.when(userWebLoader.loadUser(authValid.getUserId()))
                .thenReturn(user);

        userRepository = Mockito.spy(new UserRepository(eventBus, userWebLoader, userStorageLoader));
        authConsumer.getValue().accept(authValid);

        Mockito.verify(userWebLoader, Mockito.times(1)).loadUser(any());
        Mockito.verify(userStorageLoader, Mockito.times(0)).loadUser(any());

        Mockito.verify(eventBus, Mockito.times(1)).userPost(user);
        Mockito.verify(userStorageLoader, Mockito.times(1)).saveUser(user);
    }

    @Test
    public void loadUserStorage() throws Exception {
        Mockito.when(eventBus.authSubscribe(authConsumer.capture(), schedulerConsumer.capture()))
                .thenReturn(null);

        Mockito.when(userWebLoader.loadUser(authValid.getUserId()))
                .thenThrow(runtimeException);

        Mockito.when(userStorageLoader.loadUser(authValid.getUserId()))
                .thenReturn(user);

        userRepository = Mockito.spy(new UserRepository(eventBus, userWebLoader, userStorageLoader));
        authConsumer.getValue().accept(authValid);

        Mockito.verify(userWebLoader, Mockito.times(1)).loadUser(any());
        Mockito.verify(userStorageLoader, Mockito.times(1)).loadUser(any());

        Mockito.verify(eventBus, Mockito.times(1)).userPost(user);
        Mockito.verify(userStorageLoader, Mockito.times(0)).saveUser(user);
    }

    @Test
    public void expiredAuth() throws Exception {
        Mockito.when(eventBus.authSubscribe(authConsumer.capture(), schedulerConsumer.capture()))
                .thenReturn(null);

        Mockito.when(userWebLoader.loadUser(authValid.getUserId()))
                .thenThrow(new RequestException(requestError));

        Mockito.when(userWebLoader.handleVkError(eventBus, requestError))
                .thenReturn(true);

        userRepository = Mockito.spy(new UserRepository(eventBus, userWebLoader, userStorageLoader));
        authConsumer.getValue().accept(authValid);

        Mockito.verify(userWebLoader, Mockito.times(1)).loadUser(any());
        Mockito.verify(userStorageLoader, Mockito.times(0)).loadUser(any());

        Mockito.verify(eventBus, Mockito.times(0)).userPost(user);
        Mockito.verify(userWebLoader, Mockito.times(1)).handleVkError(eventBus, requestError);
        Mockito.verify(userStorageLoader, Mockito.times(0)).saveUser(user);
    }

    @Test
    public void requestException() throws Exception {
        Mockito.when(eventBus.authSubscribe(authConsumer.capture(), schedulerConsumer.capture()))
                .thenReturn(null);

        Mockito.when(userWebLoader.loadUser(authValid.getUserId()))
                .thenThrow(new RequestException(requestError));

        Mockito.when(userWebLoader.handleVkError(eventBus, requestError))
                .thenReturn(false);

        Mockito.when(userStorageLoader.loadUser(authValid.getUserId()))
                .thenReturn(user);

        userRepository = Mockito.spy(new UserRepository(eventBus, userWebLoader, userStorageLoader));
        authConsumer.getValue().accept(authValid);

        Mockito.verify(userWebLoader, Mockito.times(1)).loadUser(any());
        Mockito.verify(userStorageLoader, Mockito.times(1)).loadUser(any());

        Mockito.verify(eventBus, Mockito.times(1)).userPost(user);
        Mockito.verify(userWebLoader, Mockito.times(1)).handleVkError(eventBus, requestError);
        Mockito.verify(userStorageLoader, Mockito.times(0)).saveUser(user);
    }
}
