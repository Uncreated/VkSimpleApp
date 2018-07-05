package com.uncreated.vksimpleapp.model.entity.vk;

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
}
