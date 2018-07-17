package com.uncreated.vksimpleapp;

import com.uncreated.vksimpleapp.di.DaggerInstrumentedTestComponent;
import com.uncreated.vksimpleapp.di.InstrumentedTestComponent;
import com.uncreated.vksimpleapp.di.modules.ApiModule;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.responses.RequestException;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.eventbus.EventBus;
import com.uncreated.vksimpleapp.model.repository.user.UserWebLoader;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.inject.Inject;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.assertEquals;

public class UserWebLoaderInstrumentedTest {

    private static MockWebServer webServer;

    @Inject
    ApiService apiService;

    @Inject
    EventBus eventBus;

    private UserWebLoader userWebLoader;

    @BeforeClass
    public static void beforeClass() throws Exception {

        webServer = new MockWebServer();
        webServer.start();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        webServer.shutdown();
    }

    @Before
    public void before() {
        InstrumentedTestComponent component = DaggerInstrumentedTestComponent.builder()
                .apiModule(new ApiModule() {
                    @Override
                    public String apiBaseUrl() {
                        return webServer.url("/").toString();
                    }
                }).build();
        component.inject(this);

        userWebLoader = new UserWebLoader(apiService);

    }

    @Test
    public void loadUserSuccess() throws Exception {
        webServer.enqueue(userResponse("1", "Pavel", "Durov", "avatar url"));

        eventBus.authPost(new Auth("1", "token", 0L));

        User user = userWebLoader.loadUser("1");

        assertEquals(user.getId(), "1");
        assertEquals(user.getFirstName(), "Pavel");
        assertEquals(user.getLastName(), "Durov");
        assertEquals(user.getPhotoUrl(), "avatar url");
    }

    @Test
    public void loadUserError() throws Exception {
        webServer.enqueue(errorResponse(5, "token not valid"));

        eventBus.authPost(new Auth("1", "token", 0L));

        User user = null;
        try {
            user = userWebLoader.loadUser("1");
        } catch (RequestException e) {
            assertEquals(e.getRequestError().getErrorCode(), 5);
        }

        assertEquals(user, null);
    }

    private MockResponse userResponse(String userId, String firstName, String lastName, String photoMax) {
        String body = "{\n" +
                "    \"response\": [{\n" +
                "    \"id\": \"" + userId + "\",\n" +
                "    \"first_name\": \"" + firstName + "\",\n" +
                "    \"last_name\": \"" + lastName + "\",\n" +
                "    \"photo_max\": \"" + photoMax + "\"\n" +
                "  }]\n" +
                "}";
        return new MockResponse().setBody(body);
    }

    private MockResponse errorResponse(int code, String message) {
        String body = "{\n" +
                "  \"error\": {\n" +
                "    \"error_code\": \"" + code + "\",\n" +
                "    \"error_msg\": \"" + message + "\"\n" +
                "  }\n" +
                "}";
        return new MockResponse().setBody(body);
    }
}
