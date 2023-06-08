package com.pulbatte.pulbatte.plantSearch.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PlantTag {
    leaf("1"),
    flower("2"),
    fruit("3"),
    cactus("4"),
    beginner("5"),
    all("6");

    @Getter
    private final String tagCode;

    PlantTag(String tagCode) {
        this.tagCode = tagCode;
    }

    public static PlantTag enumOf(String value) {
        return Arrays.stream(PlantTag.values())
                .filter(p -> p.getTagCode().equals(value))
                .findAny().orElse(null);
    }


}