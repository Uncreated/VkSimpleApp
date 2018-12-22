package com.uncreated.vksimpleapp.model2.common;

import android.support.annotation.NonNull;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static String MD5(String s) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> boolean equals(T obj1, T obj2) {
        if (obj1 == obj2) {
            return true;
        } else if (obj1 != null) {
            return equalsNonNull(obj1, obj2);
        }
        return false;
    }

    private static <T> boolean equalsNonNull(@NonNull T obj1, T obj2) {
        return obj1.equals(obj2);
    }
}
