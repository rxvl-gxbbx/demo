package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class AddressManualDto implements Identifiable {

    private String name;

    private Integer regionId;

    @Override
    public Object getId() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressManualDto that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(regionId, that.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, regionId);
    }
}
