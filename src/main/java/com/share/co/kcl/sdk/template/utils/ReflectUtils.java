package com.share.co.kcl.sdk.template.utils;

import com.share.co.kcl.sdk.template.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReflectUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ReflectUtils.class);

    private static final String GETTER_METHOD_PREFIX = "get";
    private static final String SETTER_METHOD_PREFIX = "set";

    private ReflectUtils() {
    }

    public static String convertGetterMethodName(String fieldName) {
        return GETTER_METHOD_PREFIX + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static String convertSetterMethodName(String fieldName) {
        return SETTER_METHOD_PREFIX + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static Method getGetterMethod(Class<?> clazz, String fieldName) {
        try {
            return clazz.getMethod(convertGetterMethodName(fieldName));
        } catch (NoSuchMethodException e) {
            LOG.error("ReflectUtils执行反射获取Getter方法异常", e);
            throw new SystemException("系统繁忙");
        }
    }

    public static Method getSetterMethod(Class<?> clazz, String fieldName, Class<?> parameterTypes) {
        try {
            return clazz.getMethod(convertSetterMethodName(fieldName), parameterTypes);
        } catch (NoSuchMethodException e) {
            LOG.error("ReflectUtils执行反射获取Setter方法异常", e);
            throw new SystemException("系统繁忙");
        }
    }

    public static Object invokeGetMethod(Object o, String fieldName) {
        Method getterMethod = getGetterMethod(o.getClass(), fieldName);
        try {
            return getterMethod.invoke(o);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOG.error("ReflectUtils执行Getter方法异常", e);
            throw new SystemException("系统繁忙");
        }
    }

    public static void invokeSetMethod(Object o, String fieldName, Object args) {
        try {
            Method setterMethod = getSetterMethod(o.getClass(), fieldName, args.getClass());
            setterMethod.invoke(o, args);
        } catch (Exception e) {
            LOG.error("ReflectUtils执行Setter方法异常", e);
            throw new SystemException("系统繁忙");
        }
    }

    public static void invokeSetMethod(Object o, String fieldName, Class<?> parameterTypes, Object args) {
        try {
            Method setterMethod = getSetterMethod(o.getClass(), fieldName, parameterTypes);
            setterMethod.invoke(o, args);
        } catch (Exception e) {
            LOG.error("ReflectUtils执行Setter方法异常", e);
            throw new SystemException("系统繁忙");
        }
    }

    public static List<Field> getFieldFromClassSet(Set<Class<?>> classSet) {
        return classSet.stream()
                .map(Class::getDeclaredFields)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

}
