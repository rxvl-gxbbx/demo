package com.example.demo.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ReflectionUtils {

    public static Set<String> getAllFieldNames(Class<?> clazz) {
        Set<String> fieldNames = new HashSet<>();

        while (clazz != null && clazz != Object.class) {
            Arrays.stream(clazz.getDeclaredFields())
                    .map(Field::getName)
                    .forEach(fieldNames::add);

            clazz = clazz.getSuperclass();
        }

        return fieldNames;
    }
}
