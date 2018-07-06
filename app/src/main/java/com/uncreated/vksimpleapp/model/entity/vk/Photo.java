package com.uncreated.vksimpleapp.model.entity.vk;

import java.util.Collections;
import java.util.List;

public class Photo {
    private Long id;
    private Long ownerId;
    private List<PhotoSize> sizes;

    public Photo(Long id, Long ownerId, List<PhotoSize> sizes) {
        this.id = id;
        this.ownerId = ownerId;
        this.sizes = sizes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public List<PhotoSize> getSizes() {
        return sizes;
    }

    public void setSizes(List<PhotoSize> sizes) {
        this.sizes = sizes;
    }

    void sort() {
        Collections.sort(sizes, (o1, o2) -> o1.getWidth() - o2.getWidth());
    }

    public String getThumbnailUrl() {
        PhotoSize photoSize = sizes.get(0);
        for (int i = 1; i < sizes.size(); i++) {
            if (sizes.get(i).getWidth() > 320)
                break;
            photoSize = sizes.get(i);
        }
        return photoSize.getUrl();
    }

    public String getOriginalUrl() {
        return sizes.get(sizes.size() - 1).getUrl();
    }
}
