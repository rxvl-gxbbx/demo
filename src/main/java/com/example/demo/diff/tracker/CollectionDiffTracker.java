package com.example.demo.diff.tracker;

import com.example.demo.diff.extractor.CollectionDifferenceExtractor;
import com.example.demo.dto.Identifiable;
import com.example.demo.util.RevisionUtils;
import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffResult;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class CollectionDiffTracker<T extends Identifiable> extends DiffTracker<T>
        implements CollectionDifferenceExtractor<T> {

    protected CollectionDiffTracker(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        super(clazz);
    }

    @Override
    public Map<Object, List<Diff<?>>> collectDiffs(List<T> current, List<T> previous) {
        if (current == null) current = new ArrayList<>();
        if (previous == null) previous = new ArrayList<>();

        Map<Object, T> currentObjects = current.stream()
                .collect(Collectors.toMap(Identifiable::getId, Function.identity()));
        Map<Object, T> previousObjects = previous.stream()
                .collect(Collectors.toMap(Identifiable::getId, Function.identity()));

        Set<Object> allKeys = new HashSet<>();
        allKeys.addAll(currentObjects.keySet());
        allKeys.addAll(previousObjects.keySet());

        for (Object key : allKeys) {
            T currentObject = Objects.requireNonNullElseGet(currentObjects.get(key), this::getDefaultInstance);
            T previousObject = Objects.requireNonNullElseGet(previousObjects.get(key), this::getDefaultInstance);

            DiffResult<Object> diffResult = RevisionUtils.compare(previousObject, currentObject);

            if (diffResult.getNumberOfDiffs() != 0) {
                this.getDiffs().put(key, diffResult.getDiffs());
            }
        }

        return this.getDiffs();
    }
}
