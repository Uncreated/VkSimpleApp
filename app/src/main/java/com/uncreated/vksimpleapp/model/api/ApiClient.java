package com.uncreated.vksimpleapp.model.api;

import com.uncreated.vksimpleapp.model.entity.RequestError;
import com.uncreated.vksimpleapp.model.entity.User;
import com.uncreated.vksimpleapp.model.repository.auth.IAuthRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;

public class ApiClient {

    private ApiService apiService;

    private IAuthRepository authRepository;

    public ApiClient(ApiService apiService, IAuthRepository authRepository) {
        this.apiService = apiService;
        this.authRepository = authRepository;
    }

    public Observable<User> getUser() {
        return apiService.getUser(authRepository.getCurrentAuth().getUserId(),
                "photo")
                .subscribeOn(Schedulers.io())
                .map(userResponse -> {
                    List<User> users = userResponse.getUsers();
                    if (users != null && users.size() > 0) {
                        return users.get(0);
                    } else {
                        return throwExceptionByError(userResponse.getRequestError());
                    }
                });
    }

    private User throwExceptionByError(@Nullable RequestError error) throws Exception {
        String msg = "Error";
        if (error != null)
            msg += "(" + error.getErrorCode() + "): " + error.getErrorMsg();
        else
            msg += ": Unknown";
        throw new RuntimeException(msg);
    }
}
