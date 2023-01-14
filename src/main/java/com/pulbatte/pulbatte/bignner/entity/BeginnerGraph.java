package com.pulbatte.pulbatte.bignner.entity;

import com.pulbatte.pulbatte.bignner.dto.BeginnerRequestDto;
import com.pulbatte.pulbatte.bignner.dto.BeginnerResponseDto;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "beginnerGraph")
@Getter
@NoArgsConstructor
public class BeginnerGraph {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*@Column(name = "user_Id")*/
    private Long id;
    @OneToOne
    /*@MapsId
    @JoinColumn(name = "user_Id")*/
    private User user;
    @Column
    private LocalDate localDate;
    @Column
    private int graphValue;

    public BeginnerGraph(BeginnerRequestDto beginnerRequestDto, User user){
        this.user = user;
        this.localDate = beginnerRequestDto.getLocalDate();
        this.graphValue = beginnerRequestDto.getValue();
    }


}
