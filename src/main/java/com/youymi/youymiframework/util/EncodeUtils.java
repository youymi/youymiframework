package com.youymi.youymiframework.util;

import org.apache.commons.codec.binary.Base64;

public class EncodeUtils {
	
	 public static String decodeBase64(String code) {  
	        return new String(Base64.decodeBase64(code.getBytes()));  
	    }  
	 
	  public static byte[] decodeBase64(final byte[] bytes) {  
	        return Base64.decodeBase64(bytes);  
	    }  
	  
	    /** 
	     * 二进制数据编码为BASE64字符串 
	     * 
	     * @param bytes 
	     * @return 
	     * @throws Exception 
	     */  
	    public static String encodeBase64(final byte[] bytes) {  
	        return new String(Base64.encodeBase64(bytes));  
	    }  
	    public static String encodeBase64(String code) {  
	        return new String(Base64.encodeBase64(code.getBytes()));  
	    }  
	    
	    public static void main(String[] args) {
	    	String code = encodeBase64("邹蕊鲜");
	    	System.out.println(code);
	    	System.out.println(decodeBase64(code));
	    }
}
