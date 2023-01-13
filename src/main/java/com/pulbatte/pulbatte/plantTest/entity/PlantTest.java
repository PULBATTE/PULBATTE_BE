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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String resultTitle;
    @Column
    private int resultCode;
    @Column
    private String resultImage;
    @Column
    private String resultPlantImage;
    @Column
    @ElementCollection
    @CollectionTable
    private List<String> resultString;
    @Column
    @ElementCollection
    @CollectionTable
    private List<String> resultPlantString;


}
