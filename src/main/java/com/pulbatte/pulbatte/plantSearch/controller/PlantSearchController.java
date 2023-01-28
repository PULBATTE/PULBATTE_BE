package com.pulbatte.pulbatte.plantSearch.controller;

import com.pulbatte.pulbatte.plantSearch.dto.PlantDetailDto;
import com.pulbatte.pulbatte.plantSearch.dto.PlantListDto;
import com.pulbatte.pulbatte.plantSearch.entity.PlantTag;
import com.pulbatte.pulbatte.plantSearch.dto.PlantListResponseDto;
import com.pulbatte.pulbatte.plantSearch.service.PlantSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
public class PlantSearchController {

    private final PlantSearchService searchService;

    @GetMapping
    public ResponseEntity<Page<PlantListDto>> getAllPlants(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(searchService.getAllPlants(pageable));
    }

    // 식물 이름 검색
    @GetMapping(value = "/search", produces = "application/json; charset=utf8")
    public ResponseEntity<PlantListResponseDto> findByPlantName(
            @RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(searchService.findByPlantName(keyword));
    }

    // 카테고리별 식물 조회
    @GetMapping(value = "/{tag}", produces = "application/json; charset=utf8")
    public ResponseEntity<Page<PlantListDto>> findByPlantTag(
            @PathVariable PlantTag tag,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(searchService.findByPlantTag(tag, pageable));
    }

    // 초보자 태그 조회
    @GetMapping(value = "/beginner/{beginner}")
    public ResponseEntity<Page<PlantListDto>> findByBeginnerTag(
            @PathVariable int beginner,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(searchService.findByBeginnerTag(beginner, pageable));
    }

    // 식물 상세 조회
    @GetMapping(value = "/detail/{plantId}")
    public ResponseEntity<PlantDetailDto> findPlant(@PathVariable Long plantId) {
        return ResponseEntity.ok(searchService.getPlant(plantId));
    }
}
