package com.xinfj.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author PXIN
 * @create 2021-06-11 10:43
 */

public class Md5Utils {

    public Md5Utils() {
    }

    public static String lower32(String text) {
        return lower32(text, (String)null);
    }

    public static String lower32(String text, String charset) {
        String result = null;
        if (null != text && !"".equals(text)) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] bytes = null == charset ? text.getBytes() : text.getBytes(charset);
                md.update(bytes);
                byte[] btResult = md.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b : btResult) {
                    int bt = b & 255;
                    if (bt < 16) {
                        sb.append(0);
                    }
                    sb.append(Integer.toHexString(bt));
                }

                result = sb.toString();
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException var12) {
                var12.printStackTrace();
            }
        }

        return result;
    }

    public static String upper32(String text) {
        String result = lower32(text);
        if (null != result && !"".equals(result)) {
            result = result.toUpperCase();
        }

        return result;
    }

    public static String upper32(String text, String charset) {
        String result = lower32(text, charset);
        if (null != result && !"".equals(result)) {
            result = result.toUpperCase();
        }

        return result;
    }

    public static String lower16(String text) {
        String value = lower32(text);
        return null == value ? "" : value.substring(8, 24);
    }

    public static String upper16(String text) {
        String value = upper32(text);
        return null == value ? "" : value.substring(8, 24);
    }
}
