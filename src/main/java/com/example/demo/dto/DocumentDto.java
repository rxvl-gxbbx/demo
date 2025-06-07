package com.example.demo.dto;

import com.example.demo.util.CompareUtils;

import java.util.List;
import java.util.Objects;

public record DocumentDto(boolean value, List<FileDto> files) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentDto that)) return false;
        return value == that.value && CompareUtils.collectionEquals(files, that.files);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
