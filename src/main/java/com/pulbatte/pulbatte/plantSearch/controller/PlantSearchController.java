package com.pulbatte.pulbatte.plantSearch.controller;

import com.pulbatte.pulbatte.plant.entity.PlantTag;
import com.pulbatte.pulbatte.plantSearch.dto.PlantSearchDto;
import com.pulbatte.pulbatte.plantSearch.repository.PlantQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlantSearchController {

    private final PlantQueryRepository queryRepository;

    @GetMapping("/api/plants")
    public List<PlantSearchDto> findByPlantTag(@RequestParam PlantTag tag) {
        return queryRepository.findByPlantTag(tag);
    }
}
