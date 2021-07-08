package com.xinfj.common.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author PXIN
 * @create 2021-06-15 10:50
 * RSA非对称加密
 */
public class RsaUtils {

    private static final Map<Integer, String> KEY_MAP = new HashMap<>();

    /**
     * 随机生成密钥对
     */
    public static Map<Integer,String> genKeyPair() {
        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(1024,new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 私钥
            PrivateKey privateKey = keyPair.getPrivate();
            // 公钥
            PublicKey publicKey = keyPair.getPublic();
            String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            // 将公钥和私钥保存到Map
            KEY_MAP.put(0,publicKeyStr);
            KEY_MAP.put(1,privateKeyStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return KEY_MAP;
    }

    /**
     * RSA公钥加密
     * @param str 加密内容
     * @param publicKey  公钥
     * @return
     */
    public static String encrypt(String str, String publicKey){
        String result ="";
        try {
            //base64编码的公钥
            byte[] decoded = Base64.getDecoder().decode(publicKey);
            PublicKey pubKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            result = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchPaddingException | IllegalBlockSizeException | InvalidKeySpecException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * RSA私钥解密
     * @param str  解密内容
     * @param privateKey  私钥
     * @return
     */
    public static String decrypt(String str, String privateKey){
        String result="";
        try {
            //64位解码加密后的字符串
            byte[] strByte = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
            //base64编码的私钥
            byte[] decoded = Base64.getDecoder().decode(privateKey);
            PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            result = new String(cipher.doFinal(strByte));
        } catch (NoSuchPaddingException | IllegalBlockSizeException | InvalidKeySpecException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        genKeyPair();
        //加密字符串
        String str = "123456";
        System.out.println("随机生成的公钥为:" +KEY_MAP.get(0));
        System.out.println("随机生成的私钥为:" + KEY_MAP.get(1));
        String encryptStr = encrypt(str,genKeyPair().get(0));
        System.out.println(str + "<-------加密后的字符串-------->" + encryptStr);
        String decryptStr = decrypt(encryptStr,KEY_MAP.get(1));
        System.out.println("解密后的内容:" + decryptStr);
    }

}
