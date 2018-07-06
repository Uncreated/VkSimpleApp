package com.uncreated.vksimpleapp.model.entity.vk;

import java.util.List;

public class Gallery {

    private int count;
    private List<Photo> items;

    public Gallery(int count, List<Photo> items) {
        this.count = count;
        this.items = items;
    }

    public int getSize() {
        return count;
    }

    public List<Photo> getItems() {
        return items;
    }

    public void sort() {
        for (Photo photo : items) {
            photo.sort();
        }
    }
}
