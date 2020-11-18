package com.dong.mingjiuzhang.global.util.encryption;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

/**
 * @Author caishaodong
 * @Date 2020-09-28 17:27
 * @Description AES加密工具
 **/
public class AESUtil {
    /**
     * 算法/模式/填充
     */
    private static final String CIPHER_MODE = "AES/CBC/PKCS5Padding";
    /**
     * 秘钥
     */
    private static String DEFAULT_SECURITY_KEY = "springboot_empty";
    /**
     * 字符集
     */
    private static final String ENCODE_FORMAT = "UTF-8";

    /**
     * 创建密钥
     *
     * @param key
     * @return
     */
    private static SecretKeySpec createKey(String key) {
        key = key == null ? "" : key;
        StringBuilder sb = new StringBuilder(16);
        sb.append(key);
        while (sb.length() < 16) {
            sb.append("0");
        }
        if (sb.length() > 16) {
            sb.setLength(16);
        }

        byte[] data = null;
        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(data, "AES");
    }

    /**
     * 创建初始化向量
     *
     * @param password
     * @return
     */
    private static IvParameterSpec createIV(String password) {
        password = password == null ? "" : password;
        StringBuilder sb = new StringBuilder(16);
        sb.append(password);
        while (sb.length() < 16) {
            sb.append("0");
        }
        if (sb.length() > 16) {
            sb.setLength(16);
        }

        byte[] data = null;
        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new IvParameterSpec(data);
    }

    /**
     * AES-128/CBC算法加密字节数据
     *
     * @param content
     * @param password 密钥
     * @param iv       初始化向量
     * @return byte[]
     */
    public static byte[] aes128CBCEncrypt(byte[] content, String password, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, createKey(password), createIV(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES-128/CBC算法解密字节数组
     *
     * @param content
     * @param password 密钥
     * @param iv       初始化向量
     * @return byte[]
     */
    public static byte[] aes128CBCDecrypt(byte[] content, String password, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.DECRYPT_MODE, createKey(password), createIV(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * AES-128 CBC加密方式， 加密后使用Base64转码
     *
     * @param content        待加密内容
     * @param encodingFormat
     * @param key            密钥
     * @param initVector     初始化向量
     * @return
     * @throws Exception
     */
    public static String aesCBCEncrypt(String content, String encodingFormat, String key, String initVector) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(encodingFormat), "AES");
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec vector = new IvParameterSpec(initVector.getBytes(encodingFormat));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, vector);
            byte[] encrypted = cipher.doFinal(content.getBytes(encodingFormat));
            //此处使用BASE64做转码。
            String result = new BASE64Encoder().encode(encrypted);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES-128 CBC解密方式
     *
     * @param content        待解密的Base64字符串
     * @param encodingFormat
     * @param key            密钥
     * @param initVector     初始化向量
     * @return
     */
    public static String aesCBCDecrypt(String content, String encodingFormat, String key, String initVector) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(encodingFormat), "AES");
            IvParameterSpec vector = new IvParameterSpec(initVector.getBytes());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, vector);
            //先用base64编码，因为对应的加密使用Base64解码
            byte[] base64Bytes = new BASE64Decoder().decodeBuffer(content);
            byte[] original = cipher.doFinal(base64Bytes);
            String result = new String(original, encodingFormat);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES-128加密字符串
     *
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        return encrypt(content, DEFAULT_SECURITY_KEY);
    }

    /**
     * 加密普通内容
     */
    public static String encrypt(String content, String key) {
        key = isEmpty(key) ? DEFAULT_SECURITY_KEY : key;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes(ENCODE_FORMAT)));
            byte[] bytes = kgen.generateKey().getEncoded();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(bytes, "AES"));
            byte[] result = cipher.doFinal(content.getBytes(ENCODE_FORMAT));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                String hex = Integer.toHexString(result[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解密
     *
     * @param content
     * @return
     */
    public static String decrypt(String content) {
        return decrypt(content, DEFAULT_SECURITY_KEY);
    }

    /**
     * 解密
     *
     * @param content
     * @param securityKey
     * @return
     */
    public static String decrypt(String content, String securityKey) {
        if (isEmpty(content)) {
            return null;
        }
        securityKey = isEmpty(securityKey) ? DEFAULT_SECURITY_KEY : securityKey;
        byte[] bytes = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            bytes[i] = (byte) (high * 16 + low);
        }
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(securityKey.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] result = cipher.doFinal(bytes);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean equal(byte[] a1, byte[] a2) {
        if (a1 != null && a2 != null && a1.length == a2.length) {
            for (int i = 0; i < a1.length; i++) {
                if (a1[i] != a2[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static void main(String[] args) {
        String encryptStr = encrypt("这是需要被加密的内容");
        System.out.println("加密字符串：" + encryptStr);
        String decrypt = decrypt(encryptStr);
        System.out.println("解密后内容：" + decrypt);
    }

}
