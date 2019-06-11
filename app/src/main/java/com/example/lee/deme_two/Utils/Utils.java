package com.example.lee.deme_two.Utils;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    private static String TAG = "Utils";

    private static String getFingerPrintFromSignature(byte[] signature) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] signatureMd5Raw = md.digest(signature);
        String signatureMd5 = String.format("%032x", new BigInteger(1, signatureMd5Raw)).toUpperCase();
        Log.d(TAG, "signature md5:" + signatureMd5);
        return signatureMd5;
    }

}
