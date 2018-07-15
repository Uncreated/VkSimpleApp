package com.uncreated.vksimpleapp.model.eventbus;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.Subject;

abstract class Events {
    static <T> Disposable subscribe(Subject<T> subject, Consumer<T> consumer, Scheduler scheduler) {
        if (scheduler == null) {
            return subject.subscribe(consumer);
        } else {
            return subject.observeOn(scheduler).subscribe(consumer);
        }
    }
}
