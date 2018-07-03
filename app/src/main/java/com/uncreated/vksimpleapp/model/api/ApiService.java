package com.uncreated.vksimpleapp.model.api;

import com.uncreated.vksimpleapp.model.entity.responses.UserResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("users.get?")
    Observable<UserResponse> getUser(@Query("user_ids") String userId,
                                     @Query("fields") String fields);
}
