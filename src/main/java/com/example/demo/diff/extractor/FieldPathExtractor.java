package com.example.demo.diff.extractor;

import com.example.demo.dto.MyDiffable;

import java.util.Set;

public interface FieldPathExtractor {
    Set<String> getFieldPaths(MyDiffable current, MyDiffable previous);
}
