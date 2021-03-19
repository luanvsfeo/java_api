package com.luan.javaApi.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtils {

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String generateCriptoredPassword(String password) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            final byte[] hashbytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return StringUtils.bytesToHex(hashbytes);
        }catch (NoSuchAlgorithmException e){
            return null;
        }
    }


    public static boolean compareCriptedPasswordWithNoCriptedPassword(String criptedPassword, String noCriptedPassword){
        return criptedPassword.equals(StringUtils.generateCriptoredPassword(noCriptedPassword));
    }
}
