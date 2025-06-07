package com.example.demo.diff.tracker;

import com.example.demo.diff.extractor.FieldPathExtractor;
import com.example.demo.util.ReflectionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.Diff;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter(value = AccessLevel.PROTECTED)
public abstract class DiffTracker<T> implements FieldPathExtractor {

    protected static final short DEFAULT_SINGLE_OBJECT_ID = 0;

    private final Map<Object, List<Diff<?>>> diffs;

    private final Class<T> clazz;

    @Setter(value = AccessLevel.PROTECTED)
    private int fieldsCount;

    private final Map<Object, Set<String>> modifiedFieldNames;

    private final T defaultInstance;

    protected DiffTracker(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.clazz = clazz;
        this.diffs = new HashMap<>();
        this.modifiedFieldNames = new HashMap<>();
        this.fieldsCount = ReflectionUtils.getAllFieldNames(clazz).size();
        this.defaultInstance = clazz.getDeclaredConstructor().newInstance();
    }

    protected Map<Object, Set<String>> collectFieldNamesById(Map<Object, List<Diff<?>>> diffs) {
        for (Map.Entry<Object, List<Diff<?>>> entry : diffs.entrySet()) {
            this.getModifiedFieldNames().put(
                    entry.getKey(),
                    entry.getValue().stream()
                            .map(Diff::getFieldName)
                            .collect(Collectors.toSet())
            );

            if (this.getFieldsCount() == this.getModifiedFieldNames().size()) {
                break;
            }
        }

        return this.getModifiedFieldNames();
    }
}
