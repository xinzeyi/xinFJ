package com.xinfj.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author PXIN
 * @create 2021-06-11 13:59
 * AES对称加密解密
 */
public class AesUtils {

    /**
     * 密钥(密钥的长度必须为16位)
     */
    private static final String KEY =XinFJUtils.getUUIDStr(16);

    /**
     *
     * @param content 加密的内容
     * @return
     */
    public static String encrypt(String content){
        String encode ="";
        try {
            if(!(XinFJUtils.isEmpty(content))){
                byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
                SecretKeySpec spec = new SecretKeySpec(keyBytes, "AES");
                //算法/模式/补码方式
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE,spec);
                byte[] bytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
                //此处使用BASE64做转码功能，同时能起到2次加密的作用
                encode = new BASE64Encoder().encode(bytes);
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return encode;
    }

    /**
     *
     * @param content 解密内容
     * @return
     */
    public static String decrypt(String content){
        String result="";
        try {
            if(!(XinFJUtils.isEmpty(content))){
                byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
                SecretKeySpec spec = new SecretKeySpec(keyBytes, "AES");
                //算法/模式/补码方式
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE,spec);
                //先用base64解密
                byte[] bytes = new BASE64Decoder().decodeBuffer(content);
                byte[] doFinal = cipher.doFinal(bytes);
                result=new String(doFinal, StandardCharsets.UTF_8);
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println(XinFJUtils.getUUIDStr(16));
        String content="www.xinFJ.com";
        String encrypt = AesUtils.encrypt(content);
        System.out.println("加密内容："+content);
        System.out.println("密钥："+KEY);
        System.out.println("加密后的内容："+encrypt);
        System.out.println("解密后的内容："+ AesUtils.decrypt(encrypt));

    }
}
