package com.pulbatte.pulbatte.bignner.entity;

import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "beginner")
@Getter
@NoArgsConstructor
public class Beginner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String beginnerPlantName;       //초보자용 식물

    @Column
    private String tip;                     //식물 성장 팁

    @Column
    private String image;                   //이미지

    @Column
    private String water;                   //물양

    @Column
    private String sunshine;                //일조량

    @Column
    private String humidity;                //습도

    @Column
    private String temp;                    //온도
/*
    @OneToOne
    @JoinColumn(name = "User_id")
    private User user;*/


}
