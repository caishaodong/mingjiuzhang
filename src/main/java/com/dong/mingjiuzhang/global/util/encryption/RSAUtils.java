package com.dong.mingjiuzhang.global.util.encryption;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Author caishaodong
 * @Date 2020-09-29 17:33
 * @Description RSA加密
 **/
public class RSAUtils {
    /**
     * 缺省的2048位密钥对,可处理245个字节(81个汉字)的数据
     */
    public static String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL2wRVFmwGIaFka7Sy7EE02G/+I6dfQ9ZiEKkkfkbaHx9I3TPCtqjiHak68dFZUwnEjtgW0Y1OkILEvZYd9TidsR4x26Ohz3KsHCyhd8MQDGFGAJ1aTP/oIs8NohBGdaVcp4wCT0ZbaKF8M2KZNxUs2i92B7KKgQAhw/dVGbHqXVAgMBAAECgYAiFA8ABtO9TC6AWpmxNyy4mu5RuGsEjBtTCFErdVB07cEf0blXm3cZgelSZolAGlGZkIHWFcX6b6t1nQJGWv3C/BrbkM8/EzhqIPs+HaULl0mMzI0EBhAMtfuTF2kgUNhRnHNArwLszs3Z/hKu7QX3HfFBWULzcls9VQ2O6K4qaQJBAPVSpMO1Y4IP3XOK97hwlev9XHkp9+sNTHfcYMbhspRFCP1/1JmzuQm+niMVKhbQMsYJPHqBMr2UYSZGlEDk5HMCQQDF8cHPBA2MbKi0ML/9bozv+1rs84BMTlbXu1k8/tYguB9DZZmUD7SrNfKW3rqBEARnmQ380iVSzhoQjZUqoQKXAkAGoCbHCl0XpCoM9tHd71x9NSiMNS/27zMDgbYzaOET1BF8Fzm8tPfoZXeL5wRt/U2Bv7ocbOTb7ef5yqoyXnw1AkEAhUjpiJyhBtovqs1cqaij6//jVMQNstPZNABp8WFjC7sPChHCnOkKbXKw+5fUG42OebecrI1QJnazJExBkefC1wJAArpZOmbeKBHmE/bdFYUvD3YlTK2WP0mW1dkmr+Z4gcFV6S68ww21rKJjKojl8QtvTKs4783Kt8fDlvZFEAAX6A==";
    public static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9sEVRZsBiGhZGu0suxBNNhv/iOnX0PWYhCpJH5G2h8fSN0zwrao4h2pOvHRWVMJxI7YFtGNTpCCxL2WHfU4nbEeMdujoc9yrBwsoXfDEAxhRgCdWkz/6CLPDaIQRnWlXKeMAk9GW2ihfDNimTcVLNovdgeyioEAIcP3VRmx6l1QIDAQAB";
    /**
     * 字符集
     */
    public static String CHARSET = "utf-8";
    /**
     * 签名算法
     */
    public static final String SIGNATURE_INSTANCE = "SHA1WithRSA";


    /**
     * 生成密钥对
     *
     * @param keyLength
     * @return
     * @throws Exception
     */
    public static KeyPair getKeyPair(int keyLength) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 公钥字符串转PublicKey实例
     *
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 私钥字符串转PrivateKey实例
     *
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 公钥加密
     *
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    public static byte[] encryptByPublicKey(byte[] content) throws Exception {
        return encryptByPublicKey(content, getPublicKey(PUBLIC_KEY));
    }

    public static String encryptByPublicKey(String content, String publicKey) throws Exception {
        return new String(Base64.getEncoder().encode(encryptByPublicKey(content.getBytes(CHARSET), getPublicKey(publicKey))));

    }

    public static String encryptByPublicKey(String content) {
        String encryptStr = null;
        try {
            encryptStr = new String(Base64.getEncoder().encode(encryptByPublicKey(content.getBytes(CHARSET))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptStr;
    }

    /**
     * 私钥解密
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    public static byte[] decryptByPrivateKey(byte[] content) throws Exception {
        return decryptByPrivateKey(content, getPrivateKey(PRIVATE_KEY));
    }

    public static String decryptByPrivateKey(String content, String privateKey) throws Exception {
        return new String(decryptByPrivateKey(Base64.getDecoder().decode(content), getPrivateKey(privateKey)), CHARSET);

    }

    public static String decryptByPrivateKey(String content) {
        String decryptStr = null;
        try {
            decryptStr = new String(decryptByPrivateKey(Base64.getDecoder().decode(content)), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptStr;
    }

    /**
     * 私钥加密
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    public static byte[] encryptByPrivateKey(byte[] content) throws Exception {
        return encryptByPrivateKey(content, getPrivateKey(PRIVATE_KEY));
    }

    public static String encryptByPrivateKey(String content, String privateKey) throws Exception {
        return new String(encryptByPrivateKey(content.getBytes(CHARSET), getPrivateKey(privateKey)), CHARSET);
    }

    public static String encryptByPrivateKey(String content) throws Exception {
        return new String(encryptByPrivateKey(content.getBytes(CHARSET)), CHARSET);
    }


    /**
     * 公钥解密
     *
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decrypByPublicKey(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    public static byte[] decrypByPublicKey(byte[] content) throws Exception {
        return decrypByPublicKey(content, getPublicKey(PUBLIC_KEY));
    }

    public static String decrypByPublicKey(String content, String publicKey) throws Exception {
        return new String(decrypByPublicKey(Base64.getDecoder().decode(content), getPublicKey(publicKey)), CHARSET);

    }

    public static String decrypByPublicKey(String content) throws Exception {
        return new String(decrypByPublicKey(Base64.getDecoder().decode(content)), CHARSET);
    }

    /**
     * 签名
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] content, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_INSTANCE);
        signature.initSign(privateKey);
        signature.update(content);
        return signature.sign();
    }

    public static byte[] sign(byte[] content) throws Exception {
        return sign(content, getPrivateKey(PRIVATE_KEY));
    }

    public static String sign(String content, String privateKey) throws Exception {
        return new String(Base64.getEncoder().encode(sign(content.getBytes(CHARSET), getPrivateKey(privateKey))), CHARSET);
    }

    public static String sign(String content) throws Exception {
        return new String(Base64.getEncoder().encode(sign(content.getBytes(CHARSET))), CHARSET);
    }

    /**
     * 验签
     *
     * @param content
     * @param sign
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] content, byte[] sign, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_INSTANCE);
        signature.initVerify(publicKey);
        signature.update(content);
        return signature.verify(sign);
    }

    public static boolean verify(byte[] content, byte[] sign) throws Exception {
        return verify(content, sign, getPublicKey(PUBLIC_KEY));
    }

    public static boolean verify(String content, String sign, String publicKey) throws Exception {
        return verify(content.getBytes(CHARSET), Base64.getDecoder().decode(sign), getPublicKey(publicKey));
    }

    public static boolean verify(String content, String sign) throws Exception {
        return verify(content.getBytes(CHARSET), Base64.getDecoder().decode(sign), getPublicKey(PUBLIC_KEY));
    }

    /**
     * 生成公钥和私钥
     */
    public void getPublicAndPrivateKey() {
        try {
            KeyPair keyPair = RSAUtils.getKeyPair(1024);
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            System.out.println("公钥:" + new String(Base64.getEncoder().encode(publicKey.getEncoded())));
            System.out.println("私钥:" + new String(Base64.getEncoder().encode(privateKey.getEncoded())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String content = "123";
        // 公钥加密
        String s = encryptByPublicKey(content);
        System.out.println(s);
        // 私钥解密
        String result = decryptByPrivateKey(s);
        System.out.println(result);
    }

}
