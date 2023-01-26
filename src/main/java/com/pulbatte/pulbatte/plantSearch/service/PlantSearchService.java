package com.pulbatte.pulbatte.plantSearch.service;

import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.plantJournal.repository.PlantRepository;
import com.pulbatte.pulbatte.plantSearch.dto.PlantDetailDto;
import com.pulbatte.pulbatte.plantSearch.entity.Plant;
import com.pulbatte.pulbatte.plantSearch.entity.PlantTag;
import com.pulbatte.pulbatte.plantSearch.dto.PlantListDto;
import com.pulbatte.pulbatte.plantSearch.dto.PlantListResponseDto;
import com.pulbatte.pulbatte.plantSearch.repository.PlantQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantSearchService {

    private final PlantQueryRepository queryRepository;
    private final PlantRepository plantRepository;

//    public PlantListResponseDto getAllPlants() {
//        List<PlantListDto> plantList = queryRepository.findAll();
//        return PlantListResponseDto.builder()
//                .plants(plantList)
//                .build();
//    }

    // 무한 스크롤 처리
    public Slice<PlantListDto> getAllBySlice(Pageable pageable) {
        return queryRepository.findAllBySlice(pageable);
    }

    // 식물 이름 검색
    public PlantListResponseDto findByPlantName(String keyword) {
        List<PlantListDto> plantList = queryRepository.findByPlantName(keyword);
        return PlantListResponseDto.builder()
                .plants(plantList)
                .build();
    }

    public Slice<PlantListDto> findByPlantTag(PlantTag tag, Pageable pageable) {
//        return PlantListResponseDto.builder()
//                .plants(plantList)
//                .build();
        return queryRepository.findByPlantTag(tag, pageable);
    }

    // 식물 상세 조회
    public PlantDetailDto getPlant(Long plantId) {
        Plant plant = plantRepository.findById(plantId).orElseThrow(
                () -> new CustomException(ErrorCode.NO_PLANT_FOUND)
        );
        PlantDetailDto detailDto = queryRepository.findOne(plantId);
        return PlantDetailDto.builder()
                .plant(plant)
                .build();
    }
}
