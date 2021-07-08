package com.xinfj.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author PXIN
 * @create 2021-06-15 17:21
 * SHA256简单加密
 */
public class Sha256Utils {

    public static String getSha256Str(String str){
        String hex ="";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(str.getBytes(StandardCharsets.UTF_8));
            hex = byte2Hex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
       return hex;
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();
        String temp = "";
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
               stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }
}
