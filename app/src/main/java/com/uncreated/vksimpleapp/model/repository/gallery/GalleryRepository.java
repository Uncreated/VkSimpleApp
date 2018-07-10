package com.uncreated.vksimpleapp.model.repository.gallery;

import com.uncreated.vksimpleapp.model.EventBus;
import com.uncreated.vksimpleapp.model.api.ApiService;
import com.uncreated.vksimpleapp.model.entity.vk.Gallery;
import com.uncreated.vksimpleapp.model.entity.vk.User;
import com.uncreated.vksimpleapp.model.repository.WebRepository;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class GalleryRepository extends WebRepository {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public GalleryRepository(ApiService apiService, EventBus eventBus) {

        compositeDisposable.add(eventBus.getGallerySubject()
                .observeOn(Schedulers.io())
                .subscribe(gallery -> getGallery(apiService, eventBus, gallery.getUser())));

        compositeDisposable.add(eventBus.getUserSubject()
                .observeOn(Schedulers.io())
                .subscribe(user -> getGallery(apiService, eventBus, user)));
    }

    private Disposable getGallery(ApiService apiService, EventBus eventBus, User user) {
        int offset = 0;
        if (user.getGallery() != null) {
            offset = user.getGallery().getItems().size();
        }
        return apiService.getGallery(user.getId(), true, offset, 200)
                .subscribe(vkResponse -> {
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
                        },
                        throwable -> {
                            Timber.tag("MyDebug").d(throwable);
                        });
    }
}
