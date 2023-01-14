package com.pulbatte.pulbatte.bignner.entity;

import com.pulbatte.pulbatte.bignner.dto.BeginnerRequestDto;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "beginnerGraph")
@Getter
@NoArgsConstructor
public class BeginnerGraph {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDate localDate;
    @Column
    private int graphValue;
    @OneToOne
    private User user;
    @ManyToOne
    private BeginnerUser beginnerUser;

    public BeginnerGraph(BeginnerRequestDto beginnerRequestDto,BeginnerUser beginnerUser , User user,LocalDate localDate){
        this.user = user;
        this.beginnerUser = beginnerUser;
        this.localDate = localDate;
        this.graphValue = beginnerRequestDto.getValue();
    }


}