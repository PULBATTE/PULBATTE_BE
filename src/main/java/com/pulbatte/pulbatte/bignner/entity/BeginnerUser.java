package com.pulbatte.pulbatte.bignner.entity;

import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class BeginnerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @OneToOne
    private User user;
    @OneToOne
    private Beginner beginner;
    @OneToMany(mappedBy = "beginnerUser",cascade = CascadeType.REMOVE)
    private List<BeginnerGraph> beginnerGraphs = new ArrayList<>();

    public BeginnerUser(Beginner beginner, LocalDate startDate,User user){
        this.beginner = beginner;
        this.startDate = startDate;
        this.user = user;
    }


}