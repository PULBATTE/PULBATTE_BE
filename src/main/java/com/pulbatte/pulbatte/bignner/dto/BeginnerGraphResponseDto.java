package com.pulbatte.pulbatte.bignner.dto;

import com.pulbatte.pulbatte.bignner.entity.BeginnerGraph;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BeginnerGraphResponseDto {
    private LocalDate localDate;
    private int graphValue;

    public BeginnerGraphResponseDto(BeginnerGraph beginnerGraph){
        this.localDate = beginnerGraph.getLocalDate();
        this.graphValue = beginnerGraph.getGraphValue();
    }
}
