package com.example.demo.diff.extractor;

import org.apache.commons.lang3.builder.Diff;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface CollectionDifferenceExtractor<T> {
    Map<Object, List<Diff<?>>> collectDiffs(@Nullable List<T> current, @Nullable List<T> previous);
}
