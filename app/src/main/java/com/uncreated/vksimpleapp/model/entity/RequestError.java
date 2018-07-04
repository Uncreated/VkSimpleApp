package com.uncreated.vksimpleapp.model.entity;

import io.reactivex.annotations.Nullable;

public class RequestError {
    private int errorCode;
    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public static void throwException(@Nullable RequestError error) throws RuntimeException {
        String msg = "Error";
        if (error != null)
            msg += "(" + error.getErrorCode() + "): " + error.getErrorMsg();
        else
            msg += ": Unknown";
        throw new RuntimeException(msg);
    }
}
