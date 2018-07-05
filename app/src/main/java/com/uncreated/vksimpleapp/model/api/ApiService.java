package com.uncreated.vksimpleapp.model.api;

import com.uncreated.vksimpleapp.model.entity.responses.VkResponse;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("users.get")
    Observable<VkResponse<List<User>>> getUser(@Query("user_ids") String userId,
                                               @Query("fields") String fields);

    @GET("photos.getAll")
    Observable<VkResponse<Gallery>> getGallery(@Query("owner_id") String userId,
                                               @Query("photo_sizes") boolean photoSizes,
                                               @Query("count") int count);
}
