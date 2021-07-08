package com.xinfj.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author PXIN
 * @create 2021-06-15 9:47
 */
public class Base64Utils {

    /**
     * base64 编码
     * @param text
     * @return
     */
    public static String base64Encrypt(String text){
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * base64 解码
     * @param encodedText
     * @return
     */
    public static String base64Decrypt(String encodedText){
        byte[] decode = Base64.getDecoder().decode(encodedText);
        return new String(decode, StandardCharsets.UTF_8);
    }

    /**
     * base64另一种加密方式
     * @param content
     * @return
     */
    public static String encrypt(String content){
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encodeBuffer(content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * base64 另一种解密方式
     * @param encodedContent
     * @return
     */
    public static String decrypt(String encodedContent){
        String s = "";
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(encodedContent);
            s = new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    public static void main(String[] args) {
        String text="text";
        String encrypt = base64Encrypt(text);
        System.out.println("base64加密前的内容："+text);
        System.out.println("base64加密后的内容："+base64Encrypt(text));
        System.out.println("base64解密后的内容："+base64Decrypt(encrypt));
        String encrypt1 = encrypt("123456789");
        System.out.println("编码："+encrypt1);
        System.out.println("解码："+decrypt(encrypt1));
    }
}
