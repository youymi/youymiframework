package com.youymi.youymiframework.util;

import java.util.UUID;

public class AppUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(getUUID() + ",\n" + getUUID().length());
    }
}
