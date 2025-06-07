package com.example.demo.util;

import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CompareUtils {

    private static <T> boolean isListDifferent(@Nullable List<T> list1, @Nullable List<T> list2) {
        if (list1 == null) return list2 != null;
        if (list2 == null) return true;

        if (list1.size() != list2.size()) return true;

        List<T> diffs1 = new ArrayList<>(list1);
        list2.forEach(diffs1::remove);
        if (!diffs1.isEmpty()) return true;

        List<T> diffs2 = new ArrayList<>(list2);
        list1.forEach(diffs2::remove);

        return !diffs2.isEmpty();
    }

    public static <T> boolean collectionEquals(@Nullable List<T> list1, @Nullable List<T> list2) {
        return !isListDifferent(list1, list2);
    }
}
