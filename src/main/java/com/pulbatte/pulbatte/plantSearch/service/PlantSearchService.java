package com.pulbatte.pulbatte.plantSearch.service;

import com.pulbatte.pulbatte.plantSearch.entity.PlantTag;
import com.pulbatte.pulbatte.plantSearch.dto.PlantListDto;
import com.pulbatte.pulbatte.plantSearch.dto.PlantListResponseDto;
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

    public PlantListResponseDto findByPlantName(String keyword) {
        List<PlantListDto> plantList = queryRepository.findByPlantName(keyword);
        return PlantListResponseDto.builder()
                .plants(plantList)
                .build();
    }

    public PlantListResponseDto findByPlantTag(PlantTag tag) {
        List<PlantListDto> plantList = queryRepository.findByPlantTag(tag);
        return PlantListResponseDto.builder()
                .plants(plantList)
                .build();
    }
}
