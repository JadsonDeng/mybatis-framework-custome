package com.jadson.study.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {

    public static Object createObject(String beanClassName, Object... params) {
        try {
            Class<?> clazz = Class.forName(beanClassName);
            Constructor<?> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getTypeByFieldName(String beanClassName, String name) {
        try {
            Class<?> clazz = Class.forName(beanClassName);
            Field field = clazz.getDeclaredField(name);
            return field.getType();
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void invokeMethod(Object object, String methodName) {
        try {
            Class<?> clazz = object.getClass();
            Method method = clazz.getDeclaredMethod(methodName);
            method.invoke(object, null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void setProperty(Object object, String fieldName, Object fieldValue) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, fieldValue);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
