package com.uncreated.vksimpleapp.model;

import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.BitmapIndex;
import com.uncreated.vksimpleapp.model.repository.IndexUrl;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class EventBus {

    private BitmapEvents thumbnailEvents;
    private BitmapEvents originalEvents;

    private PublishSubject<Integer> clickThumbnailSubject = PublishSubject.create();

    private BehaviorSubject<Auth> authSubject = BehaviorSubject.create();
    private BehaviorSubject<Object> authNotValidSubject = BehaviorSubject.create();
    private BehaviorSubject<User> userSubject = BehaviorSubject.create();
    private BehaviorSubject<Gallery> gallerySubject = BehaviorSubject.create();

    public EventBus(int thumbnailCount, int originalCount) {
        thumbnailEvents = new BitmapEvents(thumbnailCount);
        originalEvents = new BitmapEvents(originalCount);
    }

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
        private ReplaySubject<Integer> indexSubject;
        private ReplaySubject<IndexUrl> urlSubject;
        private PublishSubject<BitmapIndex> bitmapSubject;

        private BitmapEvents(int size) {
            indexSubject = ReplaySubject.createWithSize(size);
            urlSubject = ReplaySubject.createWithSize(size);
            bitmapSubject = PublishSubject.create();
        }

        public ReplaySubject<Integer> getIndexSubject() {
            return indexSubject;
        }

        public ReplaySubject<IndexUrl> getUrlSubject() {
            return urlSubject;
        }

        public PublishSubject<BitmapIndex> getBitmapSubject() {
            return bitmapSubject;
        }
    }
}
