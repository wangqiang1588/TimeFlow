package com.mobibrw.utils;

import android.support.annotation.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    private static String TAG = "HashUtils";

    public static String sha1(final String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for (int i = 0; i < bits.length; i++) {
            int a = bits[i];
            if (a < 0) a += 256;
            if (a < 16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }

    @NonNull
    public static String generateSha1(final String data) {
        try {
            return HashUtils.sha1(data);
        } catch (NoSuchAlgorithmException e) {
            LogEx.e(TAG, e.getMessage());
        }
        return "";
    }
}
