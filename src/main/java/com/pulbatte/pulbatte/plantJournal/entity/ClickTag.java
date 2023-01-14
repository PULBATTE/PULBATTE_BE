package com.pulbatte.pulbatte.plantJournal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ClickTag {
    water("Water"),
    nutrition("nutrition"),
    repotting("repotting");

    private final String clickTag;

    public static ClickTag valueOfClickTag(String clickTag) {
        return Arrays.stream(values())
                .filter(value -> value.clickTag.equals(clickTag))
                .findAny()
                .orElse(null);
    }
}
