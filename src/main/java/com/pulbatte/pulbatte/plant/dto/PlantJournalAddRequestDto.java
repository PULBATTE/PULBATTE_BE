package com.pulbatte.pulbatte.plant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantJournalAddRequestDto {

    private String plantName; // 식물 이름
    private int waterCycle; // 물 주기 사이클
    private int nutritionCycle; // 영양 주기 사이클
    private int repottingCycle; // 분갈이 주기 사이클
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate LastWateringDay; // 마지막 물 준 날
    private int selectWater; // 사용자 선택 물량
    private int selcetSunshine; // 사용자 선택 일조량
    private int selcetWind; // 사용자 선택 통풍
}
