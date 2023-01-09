package com.pulbatte.pulbatte.plantSearch.service;

import com.pulbatte.pulbatte.plant.entity.Plant;
import com.pulbatte.pulbatte.plantSearch.dto.PlantListDto;
import com.pulbatte.pulbatte.plantSearch.dto.PlantListResponseDto;
import com.pulbatte.pulbatte.plantSearch.dto.PlantSearchDto;
import com.pulbatte.pulbatte.plantSearch.repository.PlantQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantSearchService {

    private final PlantQueryRepository queryRepository;

    public PlantListResponseDto getAllPlants() {
        List<PlantListDto> plantList = queryRepository.findAll();
        return PlantListResponseDto.builder()
                .plants(plantList)
                .build();
    }

    public PlantListResponseDto findByPlantName(PlantSearchDto searchDto) {
        List<PlantListDto> plantList = queryRepository.findByPlantName(searchDto);
        return PlantListResponseDto.builder()
                .plants(plantList)
                .build();
    }

    public PlantListResponseDto findByPlantTag(PlantSearchDto searchDto) {
        List<PlantListDto> plantList = queryRepository.findByPlantTag(searchDto);
        return PlantListResponseDto.builder()
                .plants(plantList)
                .build();
    }
}
