package com.pulbatte.pulbatte.bignner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;
@Getter
@NoArgsConstructor
public class BeginnerRequestDto {
    private LocalDate localDate;
    private int value;
}