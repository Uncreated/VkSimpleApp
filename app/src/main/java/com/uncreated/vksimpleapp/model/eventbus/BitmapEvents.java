package com.uncreated.vksimpleapp.model.eventbus;

import com.uncreated.vksimpleapp.model.entity.events.BitmapIndex;
import com.uncreated.vksimpleapp.model.entity.events.IndexUrl;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.Subject;

public class BitmapEvents extends Events {
    private Subject<Integer> indexSubject;
    private Subject<IndexUrl> urlSubject;
    private Subject<BitmapIndex> bitmapSubject;

    public BitmapEvents(Subject<Integer> indexSubject,
                        Subject<IndexUrl> urlSubject,
                        Subject<BitmapIndex> bitmapSubject) {
        this.indexSubject = indexSubject;
        this.urlSubject = urlSubject;
        this.bitmapSubject = bitmapSubject;
    }

    Disposable indexSubscribe(Consumer<Integer> consumer, Scheduler scheduler) {
        return subscribe(indexSubject, consumer, scheduler);
    }

    Disposable indexUrlSubscribe(Consumer<IndexUrl> consumer, Scheduler scheduler) {
        return subscribe(urlSubject, consumer, scheduler);
    }

    Disposable bitmapIndexSubscribe(Consumer<BitmapIndex> consumer, Scheduler scheduler) {
        return subscribe(bitmapSubject, consumer, scheduler);
    }

    public Publisher<BitmapIndex> getBitmapPublisher() {
        return bitmapSubject.toFlowable(BackpressureStrategy.BUFFER);
    }

    public Publisher<IndexUrl> getUrlSubject() {
        return urlSubject.toFlowable(BackpressureStrategy.BUFFER);
    }

    public Publisher<BitmapIndex> getBitmapSubject() {
        return bitmapSubject.toFlowable(BackpressureStrategy.BUFFER);
    }

    void post(Integer index) {
        indexSubject.onNext(index);
    }

    void post(IndexUrl indexUrl) {
        urlSubject.onNext(indexUrl);
    }

    void post(BitmapIndex bitmapIndex) {
        bitmapSubject.onNext(bitmapIndex);
    }
}
