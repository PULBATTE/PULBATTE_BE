package com.pulbatte.pulbatte.plantJournal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CalendarResponseDto {
    public LocalDate localDate;
    public int water;
    public int repot;
    public int nutrition;

    public CalendarResponseDto(int water, int repot, int nutrition){
        this.water = water;
        this.repot = repot;
        this.nutrition = nutrition;
    }

    public CalendarResponseDto(LocalDate localDate){
        this.localDate = localDate;
    }
    public CalendarResponseDto(LocalDate localDate, int water, int repot, int nutrition){
        this.localDate = localDate;
        this.water = water;
        this.repot = repot;
        this.nutrition = nutrition;
    }
    public void update(int water, int repot, int nutrition){
        this.water = water;
        this.repot = repot;
        this.nutrition = nutrition;
    }

}
