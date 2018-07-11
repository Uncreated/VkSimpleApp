package com.uncreated.vksimpleapp.model.entity.vk;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String photoMax;

    private Gallery gallery;

    public User(String id, String firstName, String lastName, String photoMax) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoMax = photoMax;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhotoUrl() {
        return photoMax;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
        this.gallery.setUser(this);
    }
}
