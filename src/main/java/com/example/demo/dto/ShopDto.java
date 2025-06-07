package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ShopDto implements Identifiable {

    private Integer id;

    private String name;

    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopDto shopDto)) return false;
        return Objects.equals(id, shopDto.id) && Objects.equals(name, shopDto.name) && Objects.equals(address, shopDto.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }
}
