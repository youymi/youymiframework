/*
 * Copyright (c) 2012-2014, EpicSaaS Yuan Xin technology Co., Ltd.
 * 
 * All rights reserved.
 */
package com.youymi.youymiframework.util;

import java.io.Serializable;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
 

/**
 * 简单加密
 * @author <a href="hoocoral@hotmail.com">胡昆荣</a>
 * @since JDK 1.7
 * @version 1.0, 13/05/07
 */
public class EncryptUtil implements Serializable {

    private static final long serialVersionUID = -7128703687666202104L;

    private static final Logger LOG = Logger.getLogger(EncryptUtil.class);

    /**
     * 默认key字符串
     */
    private static String DEFAULT_KEY = "www.yxtech.cn";

    /**
     * 加密算法
     */
    private static String ENCRYPT_DES = "DES";

    /**
     * 生成key
     * @param strKey - 字符串
     * @return 根据字符串生成的key
     */
    private static Key genKey(String strKey) {

        Key key = null;
        if (StringUtils.isBlank(strKey)) {
            strKey = DEFAULT_KEY;
        }

        SecretKeyFactory keyFactory;
        try {
            keyFactory = SecretKeyFactory.getInstance(ENCRYPT_DES);
            DESKeySpec keySpec = new DESKeySpec(strKey.getBytes());
            key = keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            LOG.error("没有找到指定的加密算法!", e);
        } finally {
            keyFactory = null;
        }
        return key;
    }

    /**
     * 使用默认key加密
     * @param str - 源字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String str) {
        return encrypt(str, DEFAULT_KEY);
    }

    /**
     * 使用默认key解密 
     * @param str - 源字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String str) {
        return decrypt(str, DEFAULT_KEY);
    }

    /**
     * 加密
     * @param str - 源字符串
     * @param key - key字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String str, String key) {

        // 验证参数
        if (StringUtils.isBlank(str)) {
            return str;
        }

        // 检查key是否成功生成
        Key encryptKey = genKey(key);
        if (encryptKey == null) {
            return str;
        }

        // 加密
        Cipher cipher;
        byte[] result = null;
        try {
            cipher = Cipher.getInstance(ENCRYPT_DES);
            cipher.init(Cipher.ENCRYPT_MODE, encryptKey);
            result = cipher.doFinal(str.getBytes("UTF-8"));
        } catch (Exception e) {
            LOG.warn("加密错误!", e);
            return str;
        }

        // 将加密后的2进制转16进制后返回
        return new String(parseByte2HexStr(result));
    }

    /**
     * 解密
     * @param str - 源字符串
     * @param key - key字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String str, String key) {

        // 验证参数
        if (StringUtils.isBlank(str)) {
            return str;
        }

        // 检查key是否成功生成
        Key decryptKey = genKey(key);
        if (decryptKey == null) {
            return str;
        }

        // 解密
        Cipher cipher;
        byte[] result = null;
        try {
            cipher = Cipher.getInstance(ENCRYPT_DES);
            cipher.init(Cipher.DECRYPT_MODE, decryptKey);
            result = cipher.doFinal(parseHexStr2Byte(str));
        } catch (Exception e) {
            LOG.warn("解密错误!", e);
            return "";
        }

        // 将加密后的字符串返回
        return new String(result);
    }

    /**
     * 将2进制数组转换为16进制字符串
     * @param buf - 2进制数组
     * @return 16进制字符串
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 将16进制字符串转换成2进制数组
     * @param hexStr - 16进制字符串
     * @return 2进制数组
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (StringUtils.isEmpty(hexStr)) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
        String src = EncryptUtil.encrypt("123456");
        System.out.println(src);
        System.out.println(EncryptUtil.decrypt(src));
        System.out.println("d7292428044105e2c81560def9ca1f2521c03129c0047f16".length());
        //        	Map map = System.getenv();  
        //        	Iterator it = map.entrySet().iterator();  
        //        	while(it.hasNext())  
        //        	{  
        //        	    Entry entry = (Entry)it.next();  
        //        	    System.out.print(entry.getKey()+"=");  
        //        	    System.out.println(entry.getValue());  
        //        	}  
        //        	
        //        	Properties properties = System.getProperties();  
        //        	Iterator it =  properties.entrySet().iterator();  
        //        	while(it.hasNext())  
        //        	{  
        //        	    Entry entry = (Entry)it.next();  
        //        	    System.out.print(entry.getKey()+"=");  
        //        	    System.out.println(entry.getValue());  
        //        	}  
        //        	
    }
}
