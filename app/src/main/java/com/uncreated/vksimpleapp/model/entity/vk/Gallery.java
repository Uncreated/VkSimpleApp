package com.uncreated.vksimpleapp.model.entity.vk;

import java.util.List;

public class Gallery {

    private int count;
    private List<Photo> items;

    public Gallery(int count, List<Photo> items) {
        this.count = count;
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Photo> getItems() {
        return items;
    }

    public void setItems(List<Photo> items) {
        this.items = items;
    }
}
