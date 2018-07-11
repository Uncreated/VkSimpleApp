package com.uncreated.vksimpleapp.model.repository.gallery;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.WebRepository;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class GalleryWebRepository extends WebRepository<Gallery> {

    public GalleryWebRepository(ApiService apiService, EventBus eventBus) {
        super(apiService, eventBus);
    }

    @Override
    protected void applyEventBus(ApiService apiService, EventBus eventBus) {
        compositeDisposable.add(eventBus.getGallerySubject()
                .observeOn(Schedulers.io())
                .subscribe(gallery -> {
                    if (gallery.getItems().size() < gallery.getSize()) {
                        Disposable d = getGallery(apiService, eventBus, gallery.getUser())
                                .subscribe(nextGallery -> {
                                    gallery.getItems().addAll(nextGallery.getItems());
                                    eventBus.getGallerySubject().onNext(gallery);
                                });
                    }
                }, throwable -> Timber.tag("MeDebug").e(throwable)));

        compositeDisposable.add(eventBus.getUserSubject()
                .observeOn(Schedulers.io())
                .subscribe(user -> getGallery(apiService, eventBus, user)
                                .subscribe(gallery -> eventBus.getGallerySubject().onNext(gallery)),
                        throwable -> Timber.tag("MeDebug").e(throwable)));
    }

    Observable<Gallery> getGallery(ApiService apiService, EventBus eventBus, User user) {
        int offset = 0;
        if (user.getGallery() != null) {
            offset = user.getGallery().getItems().size();
        }
        return apiService.getGallery(user.getId(), true, offset, 200)
                .map(vkResponse -> {
                    Gallery gallery = vkResponse.getResponse();
                    if (gallery != null) {
                        gallery.sort();
                        if (user.getGallery() != null) {
                            user.getGallery().getItems().addAll(gallery.getItems());
                        } else {
                            user.setGallery(gallery);
                        }
                        eventBus.getGallerySubject()
                                .onNext(user.getGallery());
                    } else {
                        errorHandle(vkResponse.getError(), eventBus);
                    }
                    return gallery;
                });
    }
}
