package com.example.demo.diff.tracker;

import com.example.demo.diff.extractor.CollectionDifferenceExtractor;
import com.example.demo.dto.Identifiable;
import com.example.demo.util.RevisionUtils;
import org.apache.commons.lang3.builder.Diff;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        for (int i = 0; i < currentObjects.size(); i++) {
            T currentObject = currentObjects.get(i);
            T previousObject;

            if (i < previousObjects.size()) previousObject = previousObjects.get(i);
            else previousObject = this.getDefaultInstance();

            this.getDiffs().put(i, RevisionUtils.compare(previousObject, currentObject).getDiffs());
        }

        return this.getDiffs();
    }
}
