/*
 * Copyright (c) 2012-2014, EpicSaaS Yuan Xin technology Co., Ltd.
 * 
 * All rights reserved.
 */
package com.youymi.youymiframework.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
	


    public static String getCookieValue(String key, HttpServletRequest request) {
        if (null == request.getCookies()) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName() != null && cookie.getName().equals(key)) {
                return cookie.getValue();
            }
        }

        return null;
    }

}
