package com.pulbatte.pulbatte.plantTest.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "plantTest")
@Getter
@NoArgsConstructor
public class PlantTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String resultTitle;             // 테스트 결과 제목
    @Column
    private int resultCode;                 // 테스트 결과 코드
    @Column
    private String resultImage;             // 테스트 결과 성격 이미지
    @Column
    private String resultPlantImage;        // 테스트 결과 식물 이미지
    @Column
    @ElementCollection
    @CollectionTable
    private List<String> resultString;      // 테스트 결과 성격 멘트
    @Column
    @ElementCollection
    @CollectionTable
    private List<String> resultPlantString; // 테스트 결과 식물 멘트


}
