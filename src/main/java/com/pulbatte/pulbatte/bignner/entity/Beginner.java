package com.pulbatte.pulbatte.bignner.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Beginner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String beginnerPlantName;                       //초보자용 식물
    @Column
    @ElementCollection
    @CollectionTable
    private List<String> tipList = new ArrayList<>();       //팁 리스트
    @Column
    private String image;                                   //이미지
    @Column
    private int water;                                      //물양
    @Column
    private int sunshine;                                   //일조량
    @Column
    private int ventilation;                                //온도
    @Column
    private String resultPlantCode;                         // 테스트 결과 코드
    @Column
    private String resultPlantString;                       // 테스트 결과 식물 문장
}
