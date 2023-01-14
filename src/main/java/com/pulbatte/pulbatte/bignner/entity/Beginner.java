package com.pulbatte.pulbatte.bignner.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "beginner")
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
    @OneToMany
    private List<BeginnerGraph>beginnerGraphs = new ArrayList<>();


}
