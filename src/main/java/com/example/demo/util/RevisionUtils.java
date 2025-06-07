package com.example.demo.util;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionDiffBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RevisionUtils {

    public static DiffResult<Object> compare(Object before, Object after) {
        ReflectionDiffBuilder.Builder<Object> builder = new ReflectionDiffBuilder.Builder<>();

        builder.setDiffBuilder(
                DiffBuilder.builder()
                        .setLeft(before)
                        .setRight(after)
                        .setStyle(ToStringStyle.DEFAULT_STYLE)
                        .build()
        );

        return builder.build().build();
    }
}
