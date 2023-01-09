package com.pulbatte.pulbatte.plantSearch.controller;

import com.pulbatte.pulbatte.plant.entity.PlantTag;
import com.pulbatte.pulbatte.plantSearch.dto.PlantListResponseDto;
import com.pulbatte.pulbatte.plantSearch.dto.PlantSearchDto;
import com.pulbatte.pulbatte.plantSearch.service.PlantSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
public class PlantSearchController {

    private final PlantSearchService searchService;

    @GetMapping
    public PlantListResponseDto getAllPlants() {
        return searchService.getAllPlants();
    }

    // 식물 이름 검색
    @GetMapping(value = "/search", produces = "application/json; charset=utf8")
    public PlantListResponseDto findByPlantName(@RequestParam String keyword, PlantSearchDto searchDto) {
        return searchService.findByPlantName(searchDto);
    }

    // 카테고리별 식물 조회
    @GetMapping(value = "/categories/{tag}", produces = "application/json; charset=utf8")
    public PlantListResponseDto findByPlantTag(@PathVariable PlantTag tag, PlantSearchDto searchDto) {
        return searchService.findByPlantTag(searchDto);
    }
}
