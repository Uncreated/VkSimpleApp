package com.uncreated.vksimpleapp.model.eventbus;

import com.uncreated.vksimpleapp.model.entity.events.BitmapIndex;
import com.uncreated.vksimpleapp.model.entity.events.IndexUrl;
import com.uncreated.vksimpleapp.model.entity.vk.Auth;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.Subject;

public class EventBus extends Events {

    private BitmapEvents thumbnailEvents;
    private BitmapEvents originalEvents;

    private Subject<Integer> clickThumbnailSubject;

    private Subject<Auth> authSubject;
    private Subject<User> userSubject;
    private Subject<Gallery> gallerySubject;

    private Subject<Integer> themeIdSubject;

    public EventBus(BitmapEvents thumbnailEvents,
                    BitmapEvents originalEvents,
                    Subject<Integer> clickThumbnailSubject,
                    Subject<Auth> authSubject,
                    Subject<User> userSubject,
                    Subject<Gallery> gallerySubject,
                    Subject<Integer> themeIdSubject) {
        this.thumbnailEvents = thumbnailEvents;
        this.originalEvents = originalEvents;
        this.clickThumbnailSubject = clickThumbnailSubject;
        this.authSubject = authSubject;
        this.userSubject = userSubject;
        this.gallerySubject = gallerySubject;
        this.themeIdSubject = themeIdSubject;
    }

    public Disposable clickThumbnailSubscribe(Consumer<Integer> consumer, Scheduler scheduler) {
        return subscribe(clickThumbnailSubject, consumer, scheduler);
    }

    public Publisher<Auth> getAuthSubject() {
        return authSubject.toFlowable(BackpressureStrategy.BUFFER);
    }

    public Disposable authSubscribe(Consumer<Auth> consumer, Scheduler scheduler) {
        return subscribe(authSubject, consumer, scheduler);
    }

    public Disposable userSubscribe(Consumer<User> consumer, Scheduler scheduler) {
        return subscribe(userSubject, consumer, scheduler);
    }

    public Disposable gallerySubscribe(Consumer<Gallery> consumer, Scheduler scheduler) {
        return subscribe(gallerySubject, consumer, scheduler);
    }

    public Disposable themeIdSubscribe(Consumer<Integer> consumer, Scheduler scheduler) {
        return subscribe(themeIdSubject, consumer, scheduler);
    }

    public Disposable thumbnailIndexSubscribe(Consumer<Integer> consumer, Scheduler scheduler) {
        return thumbnailEvents.indexSubscribe(consumer, scheduler);
    }

    public Disposable thumbnailIndexUrlSubscribe(Consumer<IndexUrl> consumer, Scheduler scheduler) {
        return thumbnailEvents.indexUrlSubscribe(consumer, scheduler);
    }

    public Disposable thumbnailBitmapIndexSubscribe(Consumer<BitmapIndex> consumer, Scheduler scheduler) {
        return thumbnailEvents.bitmapIndexSubscribe(consumer, scheduler);
    }

    public Disposable originalIndexSubscribe(Consumer<Integer> consumer, Scheduler scheduler) {
        return originalEvents.indexSubscribe(consumer, scheduler);
    }

    public Disposable originalIndexUrlSubscribe(Consumer<IndexUrl> consumer, Scheduler scheduler) {
        return originalEvents.indexUrlSubscribe(consumer, scheduler);
    }

    public Disposable originalBitmapIndexSubscribe(Consumer<BitmapIndex> consumer, Scheduler scheduler) {
        return originalEvents.bitmapIndexSubscribe(consumer, scheduler);
    }

    public void thumbnailEventsPost(Integer index) {
        thumbnailEvents.post(index);
    }

    public void thumbnailEventsPost(IndexUrl indexUrl) {
        thumbnailEvents.post(indexUrl);
    }

    public void thumbnailEventsPost(BitmapIndex bitmapIndex) {
        thumbnailEvents.post(bitmapIndex);
    }

    public void originalEventPost(Integer index) {
        originalEvents.post(index);
    }

    public void originalEventPost(IndexUrl indexUrl) {
        originalEvents.post(indexUrl);
    }

    public void originalEventPost(BitmapIndex bitmapIndex) {
        originalEvents.post(bitmapIndex);
    }

    public void clickThumbnailPost(Integer index) {
        clickThumbnailSubject.onNext(index);
    }

    public void authPost(Auth auth) {
        authSubject.onNext(auth);
    }

    public void userPost(User user) {
        userSubject.onNext(user);
    }

    public void galleryPost(Gallery gallery) {
        gallerySubject.onNext(gallery);
    }

    public void themeIdPost(Integer themeId) {
        themeIdSubject.onNext(themeId);
    }
}
