package com.uncreated.vksimpleapp.model.eventbus;

import com.uncreated.vksimpleapp.model.entity.events.BitmapIndex;
import com.uncreated.vksimpleapp.model.entity.events.IndexUrl;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.Subject;

public class EventBus extends Events {

    private BitmapEvents thumbnailEvents;
    private BitmapEvents originalEvents;

    private Subject<Integer> clickThumbnailSubject;

    private Subject<User> userSubject;
    private Subject<Gallery> gallerySubject;

    private Subject<Integer> themeIdSubject;

    public EventBus(BitmapEvents thumbnailEvents,
                    BitmapEvents originalEvents,
                    Subject<Integer> clickThumbnailSubject,
                    Subject<User> userSubject,
                    Subject<Gallery> gallerySubject,
                    Subject<Integer> themeIdSubject) {
        this.thumbnailEvents = thumbnailEvents;
        this.originalEvents = originalEvents;
        this.clickThumbnailSubject = clickThumbnailSubject;
        this.userSubject = userSubject;
        this.gallerySubject = gallerySubject;
        this.themeIdSubject = themeIdSubject;
    }

    public Disposable clickThumbnailSubscribe(Consumer<Integer> consumer, Scheduler scheduler) {
        return subscribe(clickThumbnailSubject, consumer, scheduler);
    }

    public Publisher<User> getUserPublisher() {
        return userSubject.toFlowable(BackpressureStrategy.BUFFER);
    }

    public Disposable userSubscribe(Consumer<User> consumer, Scheduler scheduler) {
        return subscribe(userSubject, consumer, scheduler);
    }

    public Publisher<Gallery> getGalleryPublisher() {
        return gallerySubject.toFlowable(BackpressureStrategy.BUFFER);
    }

    public Disposable gallerySubscribe(Consumer<Gallery> consumer, Scheduler scheduler) {
        return subscribe(gallerySubject, consumer, scheduler);
    }

    public Publisher<Integer> getThemeIdPublisher() {
        return themeIdSubject.toFlowable(BackpressureStrategy.BUFFER);
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

    public Publisher<BitmapIndex> getOriginalBitmapPublisher() {
        return originalEvents.getBitmapPublisher();
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

    public void userPost(User user) {
        userSubject.onNext(user);
    }

    public void galleryPost(Gallery gallery) {
        gallerySubject.onNext(gallery);
    }
}
