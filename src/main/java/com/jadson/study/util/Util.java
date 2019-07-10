package com.jadson.study.util;

public class Util {

    public static boolean isEmpty(String string) {
        return string == null || "".equals(string);
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

}
