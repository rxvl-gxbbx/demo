package com.example.demo.dto;

import com.example.demo.util.CompareUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class AddressAutoLineDto implements Identifiable {

    private String id;

    private List<UUID> fileIds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressAutoLineDto that)) return false;
        return Objects.equals(id, that.id) && CompareUtils.collectionEquals(fileIds, that.fileIds);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
