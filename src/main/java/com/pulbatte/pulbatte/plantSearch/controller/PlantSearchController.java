package com.pulbatte.pulbatte.plantSearch.controller;

import com.pulbatte.pulbatte.plant.entity.PlantTag;
import com.pulbatte.pulbatte.plantSearch.dto.PlantSearchDto;
import com.pulbatte.pulbatte.plantSearch.service.PlantSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
public class PlantSearchController {

    private final PlantSearchService searchService;

    // 식물 이름 검색
    @GetMapping(value = "/search", produces = "application/json; charset=utf8")
    public List<PlantSearchDto> findByPlantName(@RequestParam String keyword) {
        return searchService.findByPlantName(keyword);
    }

    // 카테고리별 식물 조회
    @GetMapping(value = "/categories/{tag}", produces = "application/json; charset=utf8")
    public List<PlantSearchDto> findByPlantTag(@PathVariable PlantTag tag) {
        return searchService.findByPlantTag(tag);
    }
}
