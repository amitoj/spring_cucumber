/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 1/28/15
 * Time: 11:33 AM
 * Signature generator for on-demand HTTPS testing
 */
public class SignatureGenerator {
//    public static void main(String[] args) {   //checkstyle conflicts
    private void generate() {
        String apiKey = "njv8n5q874kj2js9e23676y5";
        String secret = "dmrYbCMP3SWJ4QTk8Ub6ZKU2";
        long unixTime = System.currentTimeMillis() / 1000L;
        String sig = encryptMD5(apiKey + secret + unixTime);
        System.out.println(sig);
    }

    private static String encryptMD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        }
        catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("Problem with signature generation...");
        }
        return null;
    }
}
