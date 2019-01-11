package com.cm.media.util;

import java.util.Collection;
import java.util.List;

public class CollectionUtils {
    public static boolean isEmptyList(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmptyCollection(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> String stringValue(List<T> list) {
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder(list.size() * 2);
        for (T value : list) {
            builder.append(value.toString()).append(",");
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }
}
