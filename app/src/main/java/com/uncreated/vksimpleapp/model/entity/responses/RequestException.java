package com.uncreated.vksimpleapp.model.entity.responses;

import io.reactivex.annotations.Nullable;

public class RequestException extends RuntimeException {
    public RequestException(@Nullable RequestError error) {
        super(generateMessage(error));
    }

    private static String generateMessage(@Nullable RequestError error) {
        String msg = "Error";
        if (error != null)
            msg += "(" + error.getErrorCode() + "): " + error.getErrorMsg();
        else
            msg += ": Unknown";
        return msg;
    }
}
