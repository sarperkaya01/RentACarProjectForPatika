package com.example.Services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static byte[] sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input.getBytes()); // String → byte[]
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 can not founded !", e);
        }
    }
}
