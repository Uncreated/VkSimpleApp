package com.uncreated.vksimpleapp.di.modules;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.api.VkApiInterceptor;
import com.uncreated.vksimpleapp.model.api.VkErrorHandler;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    @Named("version")
    @Provides
    public String version() {
        return "5.80";
    }

    @Provides
    public ApiService apiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    public Retrofit retrofit(@Named("apiBaseUrl") String baseUrl,
                             GsonConverterFactory gsonConverterFactory,
                             OkHttpClient okHttpClient) {


        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Named("apiBaseUrl")
    @Provides
    public String apiBaseUrl() {
        return "https://api.vk.com/method/";
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    public Gson gson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides
    public OkHttpClient client(VkApiInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    public VkApiInterceptor accessTokenInterceptor(EventBus eventBus,
                                                   @Named("version") String version) {
        return new VkApiInterceptor(eventBus, version);
    }

    @Singleton
    @Provides
    public VkErrorHandler vkErrorHandler(EventBus eventBus) {
        return new VkErrorHandler(eventBus);
    }
}
