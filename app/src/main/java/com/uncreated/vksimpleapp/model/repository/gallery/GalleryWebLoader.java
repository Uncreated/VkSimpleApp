package com.uncreated.vksimpleapp.model.repository.gallery;

import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.responses.VkResponse;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.repository.WebLoader;

import retrofit2.Call;

public class GalleryWebLoader extends WebLoader<Gallery> {
    private static final int COUNT_PER_REQUEST = 200;

    private ApiService apiService;

    public GalleryWebLoader(ApiService apiService) {
        this.apiService = apiService;
    }

    Gallery loadGallery(String userId, int offset) throws Exception {
        Call<VkResponse<Gallery>> call = apiService.getCallGallery(userId, true, offset,
                COUNT_PER_REQUEST);

        Gallery gallery = load(call);
        gallery.sort();

        return gallery;
    }
}
