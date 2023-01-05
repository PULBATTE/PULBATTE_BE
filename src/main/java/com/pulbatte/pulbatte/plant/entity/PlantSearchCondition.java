package com.pulbatte.pulbatte.plant.entity;

import lombok.Data;

@Data
public class PlantSearchCondition {

    private String leaf;            // 잎을 감상하는
    private String flower;          // 꽃을 감상하는
    private String fruit;           // 열매를 감상하는
    private String beginner;            // 초보자
}
