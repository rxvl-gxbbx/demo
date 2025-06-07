package com.example.demo.diff.tracker;

import com.example.demo.diff.extractor.DifferenceExtractor;
import com.example.demo.diff.extractor.FieldPathExtractor;
import com.example.demo.util.RevisionUtils;
import org.apache.commons.lang3.builder.Diff;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Service
public abstract class ObjectDiffTracker<T> extends DiffTracker<T> implements DifferenceExtractor<T> {

    protected ObjectDiffTracker(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        super(clazz);
    }

    @Override
    public Map<Object, List<Diff<?>>> collectDiffs(@Nullable T current, @Nullable T previous) {
        if (current == null) current = this.getDefaultInstance();
        if (previous == null) previous = this.getDefaultInstance();

        this.getDiffs().put(DEFAULT_SINGLE_OBJECT_ID, RevisionUtils.compare(previous, current).getDiffs());

        return this.getDiffs();
    }
}
