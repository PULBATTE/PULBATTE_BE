package com.pulbatte.pulbatte.bignner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BeginnerPlants {

    다육이(1,"다육이"),
    뱅갈(2,"뱅갈고무나무"),
    스파티필름 (3, "스파티필름 "),
    콩고(4, "콩고"),
    테이블야자(5,"테이블야자 "),
    딜(6,"허브-딜");

    private final int beginnerPlantsId;
    private final String beginnerPlantsName;

    public static BeginnerPlants valueOfBeginnerPlants(int beginnerPlantsId){
        return Arrays.stream(values())
                .filter(value -> value.beginnerPlantsId==(beginnerPlantsId))
                .findAny()
                .orElse(null);
    }

}
