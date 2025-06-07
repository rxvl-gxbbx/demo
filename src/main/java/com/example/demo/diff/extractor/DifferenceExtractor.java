package com.example.demo.diff.extractor;

import org.apache.commons.lang3.builder.Diff;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface DifferenceExtractor<T> {
    Map<Object, List<Diff<?>>> collectDiffs(@Nullable T current, @Nullable T previous);
}
