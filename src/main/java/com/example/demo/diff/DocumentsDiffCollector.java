package com.example.demo.diff;

import com.example.demo.diff.tracker.ObjectDiffTracker;
import com.example.demo.dto.DocumentDto;
import com.example.demo.dto.DocumentsDto;
import com.example.demo.dto.MyDiffable;
import com.example.demo.util.RevisionUtils;
import org.apache.commons.lang3.builder.Diff;
import org.springframework.lang.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DocumentsDiffCollector extends ObjectDiffTracker<DocumentsDto> {

    private DocumentsDiffCollector() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(DocumentsDto.class);
    }

    public static DocumentsDiffCollector getInstance() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return new DocumentsDiffCollector();
    }

    @Override
    public Map<Object, List<Diff<?>>> collectDiffs(@Nullable DocumentsDto current, @Nullable DocumentsDto previous) {
        if (current == null) current = this.getDefaultInstance();
        if (previous == null) previous = this.getDefaultInstance();

        Set<String> keys = new HashSet<>();
        keys.addAll(current.keySet());
        keys.addAll(previous.keySet());

        for (String key : keys) {
            DocumentDto currentDocument = current.getOrDefault(key, null);
            DocumentDto previousDocument = previous.getOrDefault(key, null);

            if (currentDocument == null) {
                currentDocument = new DocumentDto(!previousDocument.value(), List.of());
            }

            if (previousDocument == null) {
                previousDocument = new DocumentDto(!currentDocument.value(), List.of());
            }


            this.getDiffs().put(key, RevisionUtils.compare(previousDocument, currentDocument).getDiffs());
        }

        return this.getDiffs();
    }

    @Override
    public Set<String> getFieldPaths(MyDiffable current, MyDiffable previous) {
        return this.collectFieldNamesById(collectDiffs((DocumentsDto) current, (DocumentsDto) previous)).keySet().stream()
                .map(key -> ((String) key))
                .collect(Collectors.toSet());
    }
}
