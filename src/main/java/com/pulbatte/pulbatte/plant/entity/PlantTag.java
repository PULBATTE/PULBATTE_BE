package com.pulbatte.pulbatte.plant.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlantTag {
    Leaf("잎을 감상하는"),
    Flower("꽃을 감상하는"),
    Fruit("열매를 감상하는"),
    Beginner("식린이가 키우기 쉬운");

    private final  String Tag;
}
