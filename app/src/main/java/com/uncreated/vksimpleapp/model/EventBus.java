package com.uncreated.vksimpleapp.model;

import com.uncreated.vksimpleapp.model.entity.events.BitmapIndex;
import com.uncreated.vksimpleapp.model.entity.events.IndexUrl;
import com.uncreated.vksimpleapp.model.entity.responses.RequestError;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class EventBus {

    private BitmapEvents thumbnailEvents;
    private BitmapEvents originalEvents;

    private Subject<Integer> clickThumbnailSubject = PublishSubject.create();

    private Subject<Auth> authSubject = BehaviorSubject.create();
    private Subject<Boolean> authNotValidSubject = BehaviorSubject.create();
    private Subject<User> userSubject = BehaviorSubject.create();
    private Subject<Gallery> gallerySubject = BehaviorSubject.create();

    private Subject<RequestError> vkErrorSubject = BehaviorSubject.create();
    private Subject<String> messageSubject = BehaviorSubject.create();

    private Subject<Integer> themeIdSubject = PublishSubject.create();

    public EventBus(int thumbnailCount, int originalCount) {
        thumbnailEvents = new BitmapEvents(thumbnailCount);
        originalEvents = new BitmapEvents(originalCount);
    }

    public Subject<Auth> getAuthSubject() {
        return authSubject;
    }

    public Subject<Boolean> getAuthNotValidSubject() {
        return authNotValidSubject;
    }

    public Subject<User> getUserSubject() {
        return userSubject;
    }

    public Subject<Gallery> getGallerySubject() {
        return gallerySubject;
    }

    public Subject<Integer> getClickThumbnailSubject() {
        return clickThumbnailSubject;
    }

    public BitmapEvents getThumbnailEvents() {
        return thumbnailEvents;
    }

    public BitmapEvents getOriginalEvents() {
        return originalEvents;
    }

    public Subject<RequestError> getVkErrorSubject() {
        return vkErrorSubject;
    }

    public Subject<String> getMessageSubject() {
        return messageSubject;
    }

    public Subject<Integer> getThemeIdSubject() {
        return themeIdSubject;
    }

    public class BitmapEvents {
        private Subject<Integer> indexSubject;
        private Subject<IndexUrl> urlSubject;
        private Subject<BitmapIndex> bitmapSubject;

        private BitmapEvents(int size) {
            indexSubject = PublishSubject.create();
            urlSubject = PublishSubject.create();
            bitmapSubject = PublishSubject.create();
        }

        public Subject<Integer> getIndexSubject() {
            return indexSubject;
        }

        public Subject<IndexUrl> getUrlSubject() {
            return urlSubject;
        }

        public Subject<BitmapIndex> getBitmapSubject() {
            return bitmapSubject;
        }
    }
}
