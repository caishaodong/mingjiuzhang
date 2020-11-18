package com.dong.mingjiuzhang.global.util.encryption;

import com.dong.mingjiuzhang.global.util.string.StringUtil;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @Author caishaodong
 * @Date 2020-09-18 11:19
 * @Description 密码加密工具
 **/
public class DigestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DigestUtil.class);

    public static final String SHA1 = "SHA-1";
    public static final int DEFAULT_BUFFER_LENGTH = 8192;
    public static final int DEFAULT_BYTES_SIZE = 8;
    public static final int DEFAULT_DIGEST_TIMES = 1024;

    public static String MD5(final String md5Str) {
        if (StringUtil.isBlank(md5Str)) {
            return null;
        }
        return DigestUtils.md5Hex(md5Str);
    }

    public static String getSha1Code(final MultipartFile fileObj) throws IOException {
        if (null == fileObj) {
            return "";
        }
        InputStream ins = null;
        try {
            ins = fileObj.getInputStream();
            return DigestUtils.sha1Hex(ins);
        } catch (Exception e) {
            return "";
        } finally {
            if (null != ins) {
                ins.close();
            }
        }
    }

    public static String getMd5(final String pathName) throws IOException {
        if (StringUtil.isBlank(pathName)) {
            return "";
        }
        InputStream ins = null;
        try {
            ins = new FileInputStream(pathName);
            return DigestUtils.md5Hex(ins);
        } catch (Exception e) {
            return "";
        } finally {
            if (null != ins) {
                ins.close();
            }
        }
    }

    public static String SHA256(final String signStrBefore) throws Exception {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(signStrBefore.getBytes());
            final byte[] origBytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < origBytes.length; ++i) {
                String tempStr = Integer.toHexString(origBytes[i] & 0xFF);
                if (tempStr.length() == 1) {
                    sb.append("0");
                }
                sb.append(tempStr);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), (Throwable) e);
            throw new Exception("sign is error");
        }
    }

    public static String SHA1(final String signStrBefore) throws Exception {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(signStrBefore.getBytes());
            final byte[] messageDigest = digest.digest();
            final StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; ++i) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), (Throwable) e);
            throw new Exception("sign is error");
        }
    }

    public static String getSha1Code(final byte[] byteArray) throws IOException {
        if (null == byteArray || byteArray.length < 1) {
            return "";
        }
        return DigestUtils.sha1Hex(byteArray);
    }

    public static byte[] digestString(final byte[] bytes, final String algorithm) {
        return digest(bytes, algorithm, null, 1);
    }

    public static byte[] digestString(final byte[] bytes, final byte[] salt, final String algorithm) {
        return digest(bytes, algorithm, salt, 1);
    }

    public static byte[] digestString(final byte[] bytes, final byte[] salt, final int counts, final String algorithm) {
        return digest(bytes, algorithm, salt, counts);
    }

    /**
     * 用盐对密码加密
     *
     * @param password
     * @param salt
     * @return
     */
    public static String digestString(final String password, final String salt) {
        final byte[] saltBytes = salt.getBytes();
        final byte[] pwdBytes = digest(getStringByte(password), "SHA-1", saltBytes, DEFAULT_DIGEST_TIMES);
        return encodeHex(pwdBytes);
    }

    public static byte[] getStringByte(final String str) {
        if (str != null && !"".equals(str.trim())) {
            try {
                return str.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 生成6位数盐值
     *
     * @return
     */
    public static String generateSalt() {
        return generateSalt(3);
    }

    /**
     * 生成盐值
     *
     * @param num
     * @return
     */
    public static String generateSalt(final int num) {
        if (num <= 0) {
            LOGGER.error("num argument must be a positive integer, larger than 0");
            throw new RuntimeException("num argument must be a positive integer, larger than 0");
        }
        final byte[] salt = new byte[num];
        final SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        StringBuilder sb = new StringBuilder();
        for (byte saltNum : salt) {
            sb.append(Integer.toString((saltNum & 0xff) + 0x100, 16).substring(1));
//            sb.append(Integer.toHexString(saltNum));
        }
        return sb.toString();
    }

    public static byte[] digestFile(final InputStream bytes, final String algorithm) throws IOException {
        return digest(bytes, algorithm);
    }

    private static byte[] digest(final byte[] bytes, final String algorithm, final byte[] salt, final int counts) {
        try {
            final MessageDigest digest = MessageDigest.getInstance(algorithm);
            if (salt != null) {
                digest.update(salt);
            }
            byte[] result = digest.digest(bytes);
            for (int i = 1; i < counts; ++i) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            LOGGER.error("general security exception occurs, detail exception is ", (Throwable) e);
            return null;
        }
    }

    private static byte[] digest(final InputStream input, final String algorithm) throws IOException {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            final byte[] buffer = new byte[8192];
            for (int read = input.read(buffer, 0, 8192); read > -1; read = input.read(buffer, 0, 8192)) {
                messageDigest.update(buffer, 0, read);
            }
            return messageDigest.digest();
        } catch (GeneralSecurityException e) {
            LOGGER.error("general security exception occurs when digest inputstream ", (Throwable) e);
            return null;
        }
    }

    public static byte[] decodeHex(final String src) {
        if (StringUtil.isBlank(src)) {
            return null;
        }
        try {
            return Hex.decodeHex(src.toCharArray());
        } catch (DecoderException e) {
            LOGGER.error("decode hex src failed, src vlaue is " + src, (Throwable) e);
            return null;
        }
    }

    public static String encodeHex(final byte[] data) {
        return Hex.encodeHexString(data);
    }

    public static void main(final String[] args) {
        try {
            String salt = generateSalt();
            String password = digestString("123456", salt);
            System.out.println("salt:" + salt);
            System.out.println("password:" + password);
            System.out.println("md5:" + MD5("123456"));
        } catch (Exception ex) {
        }
    }
}
