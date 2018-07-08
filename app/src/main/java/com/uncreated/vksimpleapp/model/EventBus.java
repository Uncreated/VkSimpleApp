package com.uncreated.vksimpleapp.model;

import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.BitmapIndex;
import com.uncreated.vksimpleapp.model.repository.IndexUrl;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class EventBus {

    private BitmapEvents thumbnailEvents = new BitmapEvents();
    private BitmapEvents originalEvents = new BitmapEvents();

    private PublishSubject<Integer> clickThumbnailSubject = PublishSubject.create();

    private BehaviorSubject<Auth> authSubject = BehaviorSubject.create();
    private BehaviorSubject<Object> authNotValidSubject = BehaviorSubject.create();
    private BehaviorSubject<User> userSubject = BehaviorSubject.create();
    private BehaviorSubject<Gallery> gallerySubject = BehaviorSubject.create();

    public BehaviorSubject<Auth> getAuthSubject() {
        return authSubject;
    }

    public BehaviorSubject<Object> getAuthNotValidSubject() {
        return authNotValidSubject;
    }

    public BehaviorSubject<User> getUserSubject() {
        return userSubject;
    }

    public BehaviorSubject<Gallery> getGallerySubject() {
        return gallerySubject;
    }

    public PublishSubject<Integer> getClickThumbnailSubject() {
        return clickThumbnailSubject;
    }

    public BitmapEvents getThumbnailEvents() {
        return thumbnailEvents;
    }

    public BitmapEvents getOriginalEvents() {
        return originalEvents;
    }

    public class BitmapEvents {
        private PublishSubject<Integer> indexSubject = PublishSubject.create();
        private PublishSubject<IndexUrl> urlSubject = PublishSubject.create();
        private PublishSubject<BitmapIndex> bitmapSubject = PublishSubject.create();

        private BitmapEvents() {
        }

        public PublishSubject<Integer> getIndexSubject() {
            return indexSubject;
        }

        public PublishSubject<IndexUrl> getUrlSubject() {
            return urlSubject;
        }

        public PublishSubject<BitmapIndex> getBitmapSubject() {
            return bitmapSubject;
        }
    }
}
