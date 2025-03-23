package com.springboot.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Utils {

    /**
     * 对字符串进行SHA-256加密
     *
     * @param str 需要加密的字符串
     * @return 加密后的字符串（十六进制表示）
     */
    public static String encrypt(String str) {
        try {
            // 创建MessageDigest对象，指定使用SHA-256算法
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // 将字符串转换为字节数组并进行加密
            byte[] hash = digest.digest(str.getBytes());
            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    /**
     * 对用户名和密码组合后进行SHA-256加密
     *
     * @param username 用户名
     * @param password 密码
     * @return 加密后的字符串（十六进制表示）
     */
    public static String encrypt(String username, String password) {
        // 将用户名和密码组合后进行加密
        return encrypt(username + password);
    }
}
